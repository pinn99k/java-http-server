package web.controller;

import Library.LibraryService;
import was.ServletManager;
import was.httpserver.*;
import was.session.SessionManager;
import web.command.CommandManager;
import web.command.CommandResult;
import web.command.*;
import web.command.action.LoginCommand;
import web.content.BookListContent;
import web.content.LoginContent;
import web.content.NavContent;
import web.template.BaseTemplate;
import util.FlashMessage;

import java.io.IOException;

public class HomeController implements HttpServlet {
    private final LibraryService libraryService;
    private final SessionManager sessionManager;
    private final CommandManager commands = new CommandManager();

    public HomeController(ServletManager manager) {
        this.libraryService = manager.getLibraryService();
        this.sessionManager = manager.getSessionManager();

        commands.register("login",
                new LoginCommand(libraryService, sessionManager));
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {

        String action = request.getParameter("action");

        if (action != null) {
            CommandResult actionResult = commands.execute(action, request);

            if (actionResult.redirect) {
                response.sendDirect(actionResult.redirectUrl);
                return;
            }
        }

        String flash = request.getParameter("flash");
        String flashMsg = FlashMessage.home(flash);

        StringBuilder page = new StringBuilder();
        if (flashMsg != null) {
            page.append(FlashMessage.wrapFlashBox(flashMsg));
        }

        String nav = NavContent.render(null);

        page.append(LoginContent.renderLogin(flashMsg));
        page.append("<hr>");

        page.append(BookListContent.renderBooks(libraryService.getAvailableBooks()));
        page.append("<hr>");

        page.append("<a class='btn' href='/exit'>EXIT</a>");

        response.writeBody(BaseTemplate.render("Home", nav, page.toString()));
    }
}
