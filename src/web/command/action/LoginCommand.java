package web.command.action;

import Library.LibraryService;
import domain.*;

import was.httpserver.*;
import was.session.SessionManager;
import web.command.Command;
import web.command.CommandResult;

public class LoginCommand implements Command {
    private final LibraryService libraryService;
    private final SessionManager sessionManager;

    public LoginCommand(LibraryService ls, SessionManager sm){
        this.libraryService = ls;
        this.sessionManager = sm;
    }

    @Override
    public CommandResult execute(HttpRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null) {
            return CommandResult.render("login");
        }

        User user = libraryService.login(username, password);

        if (user != null) {
            String sessionId = sessionManager.createSession(user);

            if (user instanceof Admin) {
                return CommandResult.redirect("/admin?sessionId=" + sessionId);

            }

            if (user instanceof RegularUser) {
                return CommandResult.redirect("/user?sessionId=" + sessionId);
            }
        }

        return CommandResult.redirect("/?flash=login_err");
    };
}

