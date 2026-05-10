package web.controller;

import Library.LibraryService;
import domain.User;
import util.FlashMessage;
import was.ServletManager;
import was.httpserver.*;
import was.session.SessionManager;
import web.command.CommandManager;
import web.command.CommandResult;
import web.command.action.*;
import web.content.*;
import web.template.BaseTemplate;

import java.io.IOException;

public class UserController implements HttpServlet {
    private final LibraryService libraryService;
    private final SessionManager sessionManager;
    private final CommandManager commands = new CommandManager();

    public UserController(ServletManager manager) {
        this.libraryService = manager.getLibraryService();
        this.sessionManager = manager.getSessionManager();
        // user 영역 action 등록
        commands.register("borrow",
                new BorrowCommand(libraryService, sessionManager));
        commands.register("return",
                new ReturnBookCommand(libraryService, sessionManager));
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String sid = request.getParameter("sessionId");
        User currentUser = (sid == null) ? null : sessionManager.getUser(sid);
        if (currentUser == null) {
            response.sendDirect("/");
            return;
        }

        String action = request.getParameter("action");
        if (action != null) {
            CommandResult actionResult = commands.execute(action, request);
            if (actionResult.redirect) {
                response.sendDirect(actionResult.redirectUrl);
                return;
            }
        }

        String flash = request.getParameter("flash");
        String flashMsg = FlashMessage.user(flash);

        StringBuilder page = new StringBuilder();
        if (flashMsg != null) {
            page.append(FlashMessage.wrapFlashBox(flashMsg));
        }

        String nav = NavContent.render(sid);

        page.append(BookListContent.renderBooks(libraryService.getAvailableBooks(), sid));
        page.append("<hr>");

        page.append(OverdueContent.renderOverdue(libraryService.getOverdueBooks(currentUser), sid));
        page.append("<hr>");

        page.append(BorrowedBookContent.renderBorrowedList(libraryService.getBorrowedBooks(currentUser), sid));
        page.append("<hr>");

        page.append("<a class='btn' href='/'>HOME</a>");

        response.writeBody(BaseTemplate.render("User", nav, page.toString()));
    }
}
