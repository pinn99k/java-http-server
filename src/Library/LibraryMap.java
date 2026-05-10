package Library;

import domain.Book;
import domain.User;

import java.util.*;

public class LibraryMap {
    private final List<Book> books;
    private final Map<String, User> users;

    public LibraryMap() {
        books = new ArrayList<>();
        users = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public boolean registerUser(User user) {
        for (User u : users.values()) {
            if (u.equals(user)) return false;
        }

        users.put(user.getUsername(), user);
        return true;
    }

    public Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    public User findUserByUsername(String username) {
        return users.get(username);
    }
}