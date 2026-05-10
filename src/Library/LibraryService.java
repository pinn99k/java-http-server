package Library;

import domain.*;

import java.util.*;

public class LibraryService {
    private final LibraryMap libraryMap;

    public LibraryService(LibraryMap libraryMap) {
        this.libraryMap = libraryMap;
    }

    public User login(String username, String password) {
        if (username == null || password == null) return null;

        for (User user : libraryMap.getUsers().values()) {
            if (user.getUsername().equals(username) &&
                    user.checkPassword(password)) {
                return user;
            }
        }
        return null;
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableList = new ArrayList<>();
        for (Book book : libraryMap.getBooks()) {
            if (book.isAvailable()) {
                availableList.add(book);
            }
        }
        return availableList;
    }

    public boolean addBook(User currentUser, String title, String author, String typeInput) {

        if (!(currentUser instanceof Admin)) return false;
        if (title == null || author == null) return false;
        if (libraryMap.findBookByTitle(title) != null) return false;

        Book book;
        switch (typeInput) {
            case "1" -> book = new PrintedBook(title, author);
            case "2" -> book = new eBook(title, author);
            default -> {
                return false;
            }
        }

        libraryMap.addBook(book);
        return true;
    }

    public boolean addUser(User currentUser, String username, String password, String typeInput) {

        if (!(currentUser instanceof Admin)) return false;
        if (username == null || password == null) return false;

        User newUser;
        switch (typeInput) {
            case "1" -> newUser = new Admin(username, password);
            case "2" -> newUser = new RegularUser(username, password);
            default -> {
                return false;
            }
        }

        return libraryMap.registerUser(newUser);
    }

    public boolean borrowBook(User user, String bookTitle) {
        if (!(user instanceof RegularUser regular)) return false;
        if (bookTitle == null) return false;

        Book book = libraryMap.findBookByTitle(bookTitle);
        if (book == null || !book.isAvailable()) return false;

        return regular.borrowBook(book);
    }

    public boolean returnBook(User user, String bookTitle) {
        if (!(user instanceof RegularUser regular)) return false;
        if (bookTitle == null) return false;

        Book book = libraryMap.findBookByTitle(bookTitle);
        if (book == null) return false;

        return regular.returnBook(book);
    }

    public List<Book> getBorrowedBooks(User currentUser) {
        if (!(currentUser instanceof RegularUser regularUser)) {
            return new ArrayList<>();
        }

        List<Book> borrowedBooks = new ArrayList<>();
        for (BorrowRecord record : regularUser.getBorrowedRecords()) {
            borrowedBooks.add(record.getBook());
        }
        return borrowedBooks;
    }

    public List<BorrowRecord> getOverdueBooks(User currentUser) {
        if (!(currentUser instanceof RegularUser regularUser)) return new ArrayList<>();

        List<BorrowRecord> overdueBooks = new ArrayList<>();
        for (BorrowRecord record : regularUser.getBorrowedRecords()) {
            if (record.isOverdue()) {
                overdueBooks.add(record);
            }
        }
        return overdueBooks;
    }

    public LibraryMap getLibraryMap() {
        return libraryMap;
    }
}
