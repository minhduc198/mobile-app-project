package book;

public class Favourite {
    private String title;
    private int imgFavour;

    public Favourite(int imgFavour, String title) {
        this.imgFavour = imgFavour;
        this.title = title;
    }

    public int getImgFavour() { return imgFavour; }
    public String getTitle() { return title; }


}
