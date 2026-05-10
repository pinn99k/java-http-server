package domain;

public class PrintedBook extends Book {
    public PrintedBook(String title, String author) {
        super(title, author);
    }

    @Override
    public String showDetails() {
        return "Printed Book: " + getTitle() + " by " + getAuthor();
    }
}
