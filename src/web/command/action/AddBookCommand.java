package web.command.action;

import Library.LibraryService;
import domain.*;
import was.httpserver.*;
import was.session.SessionManager;
import web.command.Command;
import web.command.CommandResult;

public class AddBookCommand implements Command {
    private final LibraryService libraryService;
    private final SessionManager sessionManager;

    public AddBookCommand(LibraryService ls, SessionManager sm) {
        this.libraryService = ls;
        this.sessionManager = sm;
    }

    @Override
    public CommandResult execute(HttpRequest request) {
        String sid = request.getParameter("sessionId");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String type = request.getParameter("type");

        User user = (sid == null) ? null : sessionManager.getUser(sid);
        if (user == null) return CommandResult.redirect("/");

        boolean ok = libraryService.addBook(user, title, author, type);
        String flash = ok ? "add_book_ok" : "add_book_fail";

        return CommandResult.redirect("/admin?sessionId=" + sid + "&flash=" + flash);

    }
}
