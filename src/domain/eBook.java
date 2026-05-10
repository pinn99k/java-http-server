package domain;

public class eBook extends Book {
    public eBook(String title, String author) {
        super(title, author);
    }

    @Override
    public String showDetails() {
        return "eBook: " + getTitle() + " by " + getAuthor();
    }
}
