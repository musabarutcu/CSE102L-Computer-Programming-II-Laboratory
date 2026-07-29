
public class Biography extends Book {
    private boolean autobiography;

    public Biography(String id, String title, String author, int year,
                     double rating, boolean autobiography) {
        super(id, title, author, year, rating);
        this.autobiography = autobiography;
    }

    public boolean isAutobiography() {
        return autobiography;
    }

    @Override
    public String getType() {
        return "Biography";
    }

    @Override
    public String toString() {
        return super.toString() + " | Autobiography: " + autobiography;
    }
}