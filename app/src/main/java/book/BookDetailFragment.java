package book;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.mobile.openlibraryapp.R;
import com.ramotion.foldingcell.FoldingCell;

public class BookDetailFragment extends Fragment {

    private ImageView imgBook;
    private TextView txtTitle, txtAuthor;
    private TextView txtFavoriteCount, txtCurrentlyReadingCount, txtHaveReadCount;
    private Button btnRead, btnToggleDescription;
    private ImageButton btnFavorite;
    private TextView titleViewDescription;

    private boolean isExpanded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_detail, container, false);

        FoldingCell foldingCell = rootView.findViewById(R.id.folding_cell);
        imgBook = rootView.findViewById(R.id.img_book);
        txtTitle = rootView.findViewById(R.id.title);
        txtAuthor = rootView.findViewById(R.id.author);
        txtFavoriteCount = rootView.findViewById(R.id.number_view);
        txtCurrentlyReadingCount = rootView.findViewById(R.id.number_view1);
        txtHaveReadCount = rootView.findViewById(R.id.number_view2);
        btnRead = rootView.findViewById(R.id.active_button);
        btnFavorite = rootView.findViewById(R.id.favorite_button);

        titleViewDescription = rootView.findViewById(R.id.title_view_description);
        btnToggleDescription = rootView.findViewById(R.id.btn_toggle_description);

        foldingCell.setOnClickListener(v -> foldingCell.toggle(false));


        btnToggleDescription.setOnClickListener(v -> {
            if (isExpanded) {
                titleViewDescription.setMaxLines(8);
                titleViewDescription.setEllipsize(android.text.TextUtils.TruncateAt.END);
                btnToggleDescription.setText("Show More");
                isExpanded = false;
            } else {
                titleViewDescription.setMaxLines(Integer.MAX_VALUE);
                titleViewDescription.setEllipsize(null);
                btnToggleDescription.setText("Show Less");
                isExpanded = true;
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString("book_title", "Unknown Title");
            String author = args.getString("book_author", "Unknown Author");
            int imageRes = args.getInt("book_image", R.drawable.book);
            String isbn = args.getString("book_isbn", "");
            String description = args.getString("book_description", "");
            int favoriteCount = args.getInt("book_favorite_count", 0);
            int currentlyReadingCount = args.getInt("book_currently_reading_count", 0);
            int haveReadCount = args.getInt("book_have_read_count", 0);

            txtTitle.setText(title);
            txtAuthor.setText(author);
            txtFavoriteCount.setText(String.valueOf(favoriteCount));
            txtCurrentlyReadingCount.setText(String.valueOf(currentlyReadingCount));
            txtHaveReadCount.setText(String.valueOf(haveReadCount));

            if (titleViewDescription != null) {
                titleViewDescription.setText(description);
            }

            btnRead.setText("READ");

            if (!isbn.isEmpty()) {
                String coverUrl = "https://covers.openlibrary.org/b/isbn/" + isbn + "-L.jpg";
                Glide.with(this)
                        .load(coverUrl)
                        .placeholder(imageRes)
                        .error(imageRes)
                        .into(imgBook);
            } else {
                imgBook.setImageResource(imageRes);
            }
        }

        return rootView;
    }
}
