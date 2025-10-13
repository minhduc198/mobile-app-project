package book;

public class Book1 {
    private String title;
    private String author;
    private int resId;
    private String actionView;
    private String isbn;
    private String description;
    private int favoriteCount;
    private int currentlyReadingCount;
    private int haveReadCount;

    public Book1(String title, String author, int resId, String actionView, String isbn,
                 String description, int favoriteCount, int currentlyReadingCount, int haveReadCount) {
        this.title = title;
        this.author = author;
        this.resId = resId;
        this.actionView = actionView;
        this.isbn = isbn;
        this.description = description;
        this.favoriteCount = favoriteCount;
        this.currentlyReadingCount = currentlyReadingCount;
        this.haveReadCount = haveReadCount;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public int getResId() { return resId; }
    public String getActionView() { return actionView; }
    public String getDescription() { return description; }
    public int getFavoriteCount() { return favoriteCount; }
    public int getCurrentlyReadingCount() { return currentlyReadingCount; }
    public int getHaveReadCount() { return haveReadCount; }

    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setDescription(String description) { this.description = description; }
    public void setFavoriteCount(int favoriteCount) { this.favoriteCount = favoriteCount; }
    public void setCurrentlyReadingCount(int currentlyReadingCount) { this.currentlyReadingCount = currentlyReadingCount; }
    public void setHaveReadCount(int haveReadCount) { this.haveReadCount = haveReadCount; }
}