package was.httpserver;

import Library.LibraryStorage;
import was.ServletManager;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.MyLogger.log;

public class HttpServer {

    private final ExecutorService es = Executors.newFixedThreadPool(10);
    private final int port;
    private final ServletManager servletManager;
    private volatile boolean running = true;
    private final LibraryStorage storage;

    private ServerSocket serverSocket;

    public HttpServer(int port, ServletManager servletManager, LibraryStorage storage) {
        this.port = port;
        this.servletManager = servletManager;
        this.storage = storage;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        log("HttpServer port: " + port);

        startConsoleListener();

        while (running) {
            try {
                Socket socket = serverSocket.accept();
                es.submit(new HttpRequestHandlerV4(socket, servletManager));
            } catch (IOException e) {
                if (!running) break;
            }
        }

        shutdown();
    }

    private void startConsoleListener() {
        Thread t = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (running) {
                try {
                    String line = reader.readLine();
                    if ("STOP".equals(line)) {
                        log("STOP command received");
                        running = false;
                        try {
                            serverSocket.close();
                        } catch (Exception ignored) {
                        }
                        return;
                    }
                } catch (IOException ignored) {
                }
            }
        });

        t.setDaemon(true);
        t.start();
    }

    private void shutdown() {
        log("Shutting down server...");

        es.shutdownNow();

        try {
            storage.save(servletManager.getLibraryService().getLibraryMap());
            log("Library saved.");
        } catch (Exception e) {
            log(e);
        }

        log("Server exit.");
    }
}
