public abstract class Book {
    protected String id;
    protected String title;
    protected String author;
    protected int year;
    protected double rating;

    public Book(String id, String title, String author, int year, double rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return String.format("[ID: %s] %s (%d) - %s | Rating: %.1f | Type: %s",
                id, title, year, author, rating, getType());
    }
}
