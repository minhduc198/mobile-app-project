package category;

import java.util.List;

import book.Book1;

public class Category {

    private String nameCate;
    private List<Book1> books;

    public Category(String nameCate, List<Book1> books) {
        this.nameCate = nameCate;
        this.books = books;
    }

    public String getNameCate() {
        return nameCate;
    }

    public List<Book1> getBooks() {
        return books;
    }
}

