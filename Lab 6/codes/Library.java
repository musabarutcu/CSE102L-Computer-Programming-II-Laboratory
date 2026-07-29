import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Library {
    private Map<String, Book> books = new HashMap<>();
    private Set<String> readBooks = new HashSet<>();
    private Map<String, Set<String>> typeIndex = new HashMap<>();

    public boolean addBook(Book b) {
        if (books.containsKey(b.getId())) {
            return false;
        }
        
        books.put(b.getId(), b);
        
        String type = b.getType();
        if (!typeIndex.containsKey(type)) {
            typeIndex.put(type, new HashSet<>());
        }
        typeIndex.get(type).add(b.getId());
        
        return true;
    }

    public Book findBook(String id) {
        return books.get(id);
    }

    public boolean markAsRead(String id) {
        if (!books.containsKey(id)) {
            return false;
        }
        return readBooks.add(id);
    }

    public boolean markAsUnread(String id) {
        return readBooks.remove(id);
    }

    public boolean isRead(String id) {
        return readBooks.contains(id);
    }

    public Set<Book> listAllBooks() {
        return new HashSet<>(books.values());
    }

    public Set<Book> listReadBooks() {
        Set<Book> result = new HashSet<>();
        for (String id : readBooks) {
            result.add(books.get(id));
        }
        return result;
    }

    public Set<Book> listUnreadBooks() {
        Set<Book> result = new HashSet<>();
        for (Book b : books.values()) {
            if (!readBooks.contains(b.getId())) {
                result.add(b);
            }
        }
        return result;
    }

    public Set<Book> listBooksByType(String type) {
        Set<Book> result = new HashSet<>();
        Set<String> ids = typeIndex.get(type);
        
        if (ids != null) {
            for (String id : ids) {
                result.add(books.get(id));
            }
        }
        return result;
    }

    public Map<String, Integer> typeStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : typeIndex.entrySet()) {
            stats.put(entry.getKey(), entry.getValue().size());
        }
        return stats;
    }
}