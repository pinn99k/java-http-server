package web.content;

import domain.Book;
import domain.User;
import util.PageUtil;

import java.util.List;

public class BookListContent {

    public static String renderBooks(List<Book> books) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>대출 가능한 도서 목록</h2>");

        for (Book b : books) {
            sb.append("<li>")
                    .append(PageUtil.escape(b.getTitle()))
                    .append(" - ")
                    .append(PageUtil.escape(b.getAuthor()))
                    .append("</li>");
        }
        return sb.toString();
    }

    public static String renderBooks(List<Book> books, String sid) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h2>대출 가능한 도서 목록</h2>");
        sb.append("<ul>");

        for (Book book : books) {
            String title = book.getTitle();
            sb.append("<li>")
                    .append(PageUtil.escape(title))
                    .append(" - ")
                    .append(PageUtil.escape(book.getAuthor()))
                    .append("</li>");

            sb.append("<a class='btn' href='/user?sessionId=")
                    .append(sid)
                    .append("&action=borrow&title=")
                    .append(title)
                    .append("'>대출</a>");

            sb.append("</li>");
        }

        sb.append("</ul>");

        return sb.toString();
    }
}
