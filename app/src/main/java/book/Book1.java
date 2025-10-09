package book;

public class Book1 {
    private String title;
    private String author;
    private int resId;
    private String actionView;
    private String isbn;

    public Book1(int resId, String actionView, String isbn){
        this.resId = resId;
        this.actionView = actionView;
        this.isbn = isbn;
    }

    public Book1(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book1(String title, String author, int resId, String actionView, String isbn) {
        this.title = title;
        this.author = author;
        this.resId = resId;
        this.actionView = actionView;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {return isbn;}




    public int getResId() {
        return resId;
    }

    public String getActionView() {
        return actionView;
    }

    public void setIsbn(String isbn) {this.isbn = isbn;}
}
