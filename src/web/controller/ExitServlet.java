package web.controller;

import was.httpserver.*;

public class ExitServlet implements HttpServlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>Goodbye!</h1>");
        response.writeBody("<a href='/'>home</a>");
    }
}
