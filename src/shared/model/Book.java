package shared.model;

public class Book {
    private String name, author;
    private int id;

    public Book() {}

    public Book(String name, String author, int id) {
        this.name = name;
        this.author = author;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", id=" + id +
                '}';
    }
}
