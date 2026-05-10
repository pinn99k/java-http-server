package domain;

import java.time.LocalDateTime;
import java.util.*;

public class RegularUser extends User {
    private final List<BorrowRecord> borrowedRecords;

    public RegularUser(String username, String password) {
        super(username, password);
        this.borrowedRecords = new ArrayList<>();
    }

    public boolean borrowBook(Book book) {
        if (book.isAvailable()) {
            book.borrow();
            addRecord(new BorrowRecord(book, LocalDateTime.now()));
            return true;
        } else {
            return false;
        }
    }

    public boolean returnBook(Book book) {
        BorrowRecord target = null;

        for (BorrowRecord record : borrowedRecords) {
            if (record.getBook().equals(book)) {
                target = record;
                break;
            }
        }

        if (target != null) {
            book.returnBook();
            borrowedRecords.remove(target);
            return true;
        }

        return false;
    }

    public void addRecord(BorrowRecord record) {
        borrowedRecords.add(record);
    }

    public List<BorrowRecord> getBorrowedRecords() {
        return borrowedRecords;
    }
}
