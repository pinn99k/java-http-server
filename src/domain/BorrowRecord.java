package domain;

import java.time.LocalDateTime;

public class BorrowRecord {
    private final Book book;
    private final LocalDateTime borrowDate;
    private final LocalDateTime dueDate;
    private static final int LOAN_TERM = 10;

    public BorrowRecord(Book book, LocalDateTime borrowDate) {
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusSeconds(LOAN_TERM);
    }

    public Book getBook() {
        return book;
    }

    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(dueDate);
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    @Override
    public String toString() {
        return book.getTitle() + " (Borrow: " + borrowDate + ", Due: " + dueDate + ")";
    }
}

