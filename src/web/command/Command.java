package web.command;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;

public interface Command {
    CommandResult execute(HttpRequest request);
}
