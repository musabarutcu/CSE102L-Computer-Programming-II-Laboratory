public class Novel extends Book {
    private int pageCount;

    public Novel(String id, String title, String author, int year,
                 double rating, int pageCount) {
        super(id, title, author, year, rating);
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public String getType() {
        return "Novel";
    }

    @Override
    public String toString() {
        return super.toString() + " | Pages: " + pageCount;
    }
}
