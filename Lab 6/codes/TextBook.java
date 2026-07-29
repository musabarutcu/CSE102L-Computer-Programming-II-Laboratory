public class TextBook extends Book {
    private String subject;

    public TextBook(String id, String title, String author, int year,
                    double rating, String subject) {
        super(id, title, author, year, rating);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String getType() {
        return "TextBook";
    }

    @Override
    public String toString() {
        return super.toString() + " | Subject: " + subject;
    }
}
