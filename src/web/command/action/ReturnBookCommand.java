package web.command.action;

import Library.LibraryService;
import domain.User;
import was.ServletManager;
import was.httpserver.*;
import was.session.SessionManager;
import web.command.Command;
import web.command.CommandResult;

public class ReturnBookCommand implements Command {
    private final LibraryService libraryService;
    private final SessionManager sessionManager;

    public ReturnBookCommand(LibraryService ls, SessionManager sm) {
        this.libraryService = ls;
        this.sessionManager = sm;
    }

    @Override
    public CommandResult execute(HttpRequest request) {
        String sid = request.getParameter("sessionId");
        String title = request.getParameter("title");

        User user = (sid == null) ? null : sessionManager.getUser(sid);
        if (user == null) return CommandResult.redirect("/");

        boolean ok = libraryService.returnBook(user, title);

        String flash = ok ? "return_ok" : "return_fail";
        return CommandResult.redirect("/user?sessionId=" + sid + "&flash=" + flash);
    }
}
