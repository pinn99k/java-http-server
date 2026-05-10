package web.content;

import domain.Book;
import util.PageUtil;

import java.util.List;

public class BorrowedBookContent {
    public static String renderBorrowedList(List<Book> borrowed, String sid) {

        StringBuilder sb = new StringBuilder();

        sb.append("<h2>대출 가능한 도서 목록</h2>");
        sb.append("<ul>");

        for (Book book : borrowed) {
            String title = book.getTitle();
            sb.append("<li>")
                    .append(PageUtil.escape(title))
                    .append(" - ")
                    .append(PageUtil.escape(book.getAuthor()))
                    .append("</li>");

            sb.append("<a class='btn' href='/user?sessionId=")
                    .append(sid)
                    .append("&action=return&title=")
                    .append(title)
                    .append("'>반납</a>");

            sb.append("</li>");
        }

        sb.append("</ul>");

        return sb.toString();
    }
}
