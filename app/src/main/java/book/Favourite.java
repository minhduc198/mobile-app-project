package book;

public class Favourite {
    private int imgFavour;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private int favoriteCount;
    private int currentlyReadingCount;
    private int haveReadCount;

    public Favourite(int imgFavour, String title, String author, String isbn,
                     String description, int favoriteCount,
                     int currentlyReadingCount, int haveReadCount) {
        this.imgFavour = imgFavour;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.favoriteCount = favoriteCount;
        this.currentlyReadingCount = currentlyReadingCount;
        this.haveReadCount = haveReadCount;
    }

    // Getters
    public int getImgFavour() { return imgFavour; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getDescription() { return description; }
    public int getFavoriteCount() { return favoriteCount; }
    public int getCurrentlyReadingCount() { return currentlyReadingCount; }
    public int getHaveReadCount() { return haveReadCount; }

}
