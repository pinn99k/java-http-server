package was;

import Library.*;
import was.httpserver.HttpServer;
import was.session.SessionManager;
import web.controller.UserController;
import web.controller.AdminController;
import web.controller.ExitServlet;
import web.controller.HomeController;

import java.io.IOException;

public class ServerMainV4 {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        LibraryMap libraryMap = new LibraryMap();
        LibraryStorage storage = new LibraryStorage();
        storage.loadInto(libraryMap);

        LibraryService libraryService = new LibraryService(libraryMap);
        SessionManager sessionManager = new SessionManager();

        ServletManager servletManager = new ServletManager(libraryService, sessionManager);

        servletManager.add("/", new HomeController(servletManager));
        servletManager.add("/admin", new AdminController(servletManager));
        servletManager.add("/user", new UserController(servletManager));

        servletManager.add("/exit", new ExitServlet()); 

        HttpServer server = new HttpServer(PORT, servletManager, storage);
        server.start();
    }
}