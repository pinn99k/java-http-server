package Library;

import domain.*;

import java.io.*;
import java.time.LocalDateTime;

import static util.MyLogger.log;

public class LibraryStorage {

    private static final String FILE_USERS = "users.txt";
    private static final String FILE_BOOKS = "books.txt";
    private static final String FILE_BORROW = "borrow.txt";

    private static final String DELIM_CHAR = "|";
    private static final String DELIM_REGEX = "\\|";

    public void save(LibraryMap map) {
        try {
            saveUsers(map);
            saveBooks(map);
            saveBorrowRecords(map);
        } catch (IOException e) {
            log(e);
        }
    }

    public void loadInto(LibraryMap map) {
        boolean userFileExists = new File(FILE_USERS).exists();
        boolean bookFileExists = new File(FILE_BOOKS).exists();

        if (!userFileExists && !bookFileExists) {
            initializeFirstTime(map);
            return;
        }

        try {
            loadUsers(map);
            loadBooks(map);
            loadBorrowRecords(map);
        } catch (IOException e) {
            log(e);
        }
    }


    private void initializeFirstTime(LibraryMap map) {
        map.registerUser(new Admin("admin", "123"));
        map.registerUser(new RegularUser("jack", "456"));
        map.registerUser(new RegularUser("yop", "789"));

        map.addBook(new PrintedBook("The Little Prince", "Saint-Exupery"));
        map.addBook(new eBook("Animal Farm: A Fairy Story", "George Orwell"));
        map.addBook(new PrintedBook("The Divine Comedy", "Dante"));
        map.addBook(new eBook("The Stranger", "Albert Camus"));
    }

    private void saveUsers(LibraryMap map) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_USERS))) {
            for (User user : map.getUsers().values()) {
                String type = (user instanceof Admin) ? "0" : "1";
                writer.write(user.getUsername() + DELIM_CHAR + user.getPassword() + DELIM_CHAR + type);
                writer.newLine();
            }
        }
    }

    private void saveBooks(LibraryMap map) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_BOOKS))) {
            for (Book book : map.getBooks()) {
                String type = (book instanceof PrintedBook) ? "0" : "1";
                writer.write(book.getTitle() + DELIM_CHAR + book.getAuthor() + DELIM_CHAR + type);
                writer.newLine();
            }
        }
    }

    private void saveBorrowRecords(LibraryMap map) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_BORROW))) {
            for (User user : map.getUsers().values()) {
                if (user instanceof RegularUser regular) {
                    for (BorrowRecord record : regular.getBorrowedRecords()) {
                        writer.write(user.getUsername() + DELIM_CHAR + record.getBook().getTitle() + DELIM_CHAR + record.getBorrowDate().toString() + DELIM_CHAR);
                        writer.newLine();
                    }
                }
            }
        }
    }


    private void loadUsers(LibraryMap map) throws IOException {
        File file = new File(FILE_USERS);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(DELIM_REGEX);
                String name = p[0];
                String pass = p[1];
                String type = p[2];

                if (type.equals("0")) {
                    map.registerUser(new Admin(name, pass));
                } else map.registerUser(new RegularUser(name, pass));
            }
        }
    }

    private void loadBooks(LibraryMap map) throws IOException {
        File file = new File(FILE_BOOKS);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(DELIM_REGEX);
                String title = p[0];
                String author = p[1];
                String type = p[2];

                Book book = type.equals("0") ? new PrintedBook(title, author) : new eBook(title, author);

                map.addBook(book);
            }
        }
    }

    private void loadBorrowRecords(LibraryMap map) throws IOException {
        File file = new File(FILE_BORROW);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(DELIM_REGEX);

                String username = p[0];
                String bookTitle = p[1];
                String borrowDateStr = p[2];

                User u = map.findUserByUsername(username);
                if (!(u instanceof RegularUser regular)) continue;

                Book b = map.findBookByTitle(bookTitle);
                if (b == null) continue;

                LocalDateTime borrowDate = LocalDateTime.parse(borrowDateStr);

                regular.addRecord(new BorrowRecord(b, borrowDate));
                b.borrow();
            }
        }
    }
}
