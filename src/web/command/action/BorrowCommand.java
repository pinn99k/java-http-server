package web.command.action;

import Library.LibraryService;
import domain.*;
import was.httpserver.*;
import was.session.SessionManager;
import web.command.Command;
import web.command.CommandResult;
import web.content.BorrowContent;

public class BorrowCommand implements Command {
    private final LibraryService libraryService;
    private final SessionManager sessionManager;

    public BorrowCommand(LibraryService ls, SessionManager sm) {
        this.libraryService = ls;
        this.sessionManager = sm;
    }

    @Override
    public CommandResult execute(HttpRequest request) {
        String sid = request.getParameter("sessionId");
        String title = request.getParameter("title");

        User user = (sid == null) ? null : sessionManager.getUser(sid);
        if (user == null) return CommandResult.redirect("/");


        boolean ok = libraryService.borrowBook(user, title);
        String flash = BorrowContent.renderBorrowResult(ok, title);

        return CommandResult.redirect("/user?sessionId=" + sid + "&flash=" + flash);
    }
}
