package web.command.action;

import Library.LibraryService;
import domain.User;
import was.httpserver.*;
import was.session.SessionManager;
import web.command.Command;
import web.command.CommandResult;

public class AddUserCommand implements Command {
    private final LibraryService libraryService;
    private final SessionManager sessionManager;

    public AddUserCommand(LibraryService ls, SessionManager sm) {
        this.libraryService = ls;
        this.sessionManager = sm;
    }

    @Override
    public CommandResult execute(HttpRequest request) {
        String sid = request.getParameter("sessionId");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String type = request.getParameter("type");

        User user = (sid == null) ? null : sessionManager.getUser(sid);
        if (user == null) return CommandResult.redirect("/");

        boolean ok = libraryService.addUser(user, username, password, type);
        String flash = ok ? "add_user_ok" : "add_user_fail";

        return CommandResult.redirect("/admin?sessionId=" + sid + "&flash=" + flash);
    }
}
