package book;

import android.widget.ImageView;
import android.widget.TextView;

public class Favourite {
    private String title;
    private String isbn;

    public Favourite(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }


}
