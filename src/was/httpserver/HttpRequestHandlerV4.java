package was.httpserver;

import was.ServletManager;

import java.io.*;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8; //UTF를 편하게 쓰기 위해 작성
import static util.MyLogger.log;

public class HttpRequestHandlerV4 implements Runnable {
    private final Socket socket;
    private final ServletManager servletManager;

    public HttpRequestHandlerV4(Socket socket, ServletManager servletManager) {
        this.socket = socket;
        this.servletManager = servletManager;
    }

    @Override
    public void run() {
        try {
            process();
        } catch (Exception e) {
            log(e);
        }
    }

    private void process() throws IOException {
        try (socket;
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), false, UTF_8)) {
            HttpRequest request = new HttpRequest(reader);
            request.setServletManager(servletManager);
            HttpResponse response = new HttpResponse(writer);

            if (request.getPath().equals("/favicon.ico")) {
                log("favicon requests");
                return;
            }
            log("=== HTTP Request ===");
            System.out.println(request);
            servletManager.execute(request, response);
            response.flush();
        }
    }
}