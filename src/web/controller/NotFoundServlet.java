package web.controller;

import was.httpserver.*;

public class NotFoundServlet implements HttpServlet {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatusCode(404);
        response.writeBody("<h1>404 페이지를찾을수없습니다.</h1>");
        response.writeBody("<a href='/'>home</a>");
    }
}
