package book;

public class Book1 {
    private String title;
    private String author;
    private int resId;
    private String actionView;

    public Book1(int resId, String actionView) {
        this.resId = resId;
        this.actionView = actionView;
    }

    public Book1(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getResId() {
        return resId;
    }

    public String getActionView() {
        return actionView;
    }
}
