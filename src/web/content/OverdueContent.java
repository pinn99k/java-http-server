package web.content;

import domain.Book;
import domain.BorrowRecord;
import util.PageUtil;

import java.util.List;

public class OverdueContent {

    public static String renderOverdue(List<BorrowRecord> overdue, String sid) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>연체 도서 목록</h2>");
        sb.append("<ul>");

        for (BorrowRecord record : overdue) {
            Book book = record.getBook();
            sb.append("<li>");
            sb.append(PageUtil.escape(book.getTitle()))
                    .append(" - ")
                    .append(PageUtil.escape(book.getAuthor()))
                    .append(" (대여일: ")
                    .append(record.getBorrowDate())
                    .append(")");
            sb.append("</li>");
        }
        sb.append("</ul>");
        return sb.toString();
    }
}
