package book;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.google.android.material.snackbar.Snackbar;
import android.widget.Toast;


public class BookDetailFragment extends Fragment {

    private ImageView imgBook;
    private TextView txtTitle, txtAuthor, titleViewDescription;
    private TextView txtFavoriteCount, txtCurrentlyReadingCount, txtHaveReadCount;
    private Button btnRead, btnToggleDescription;
    private ImageButton btnFavorite;

    private boolean isExpanded = false;
    private boolean isFavorite = false;
    private String bookKey = "";

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
                titleViewDescription.setMaxLines(4);
                titleViewDescription.setEllipsize(android.text.TextUtils.TruncateAt.END);
                btnToggleDescription.setText("Show More");
            } else {
                titleViewDescription.setMaxLines(Integer.MAX_VALUE);
                titleViewDescription.setEllipsize(null);
                btnToggleDescription.setText("Show Less");
            }
            isExpanded = !isExpanded;
        });

        // Nhận dữ liệu từ Bundle
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
            titleViewDescription.setText(description);
            btnRead.setText("READ");

            // Dùng ISBN làm key (nếu không có thì dùng title)
            bookKey = !isbn.isEmpty() ? isbn : title;

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

            // Load trạng thái tim đã lưu
            SharedPreferences prefs = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE);
            isFavorite = prefs.getBoolean(bookKey, false);
            updateHeartIcon();

            btnFavorite.setOnClickListener(v -> {
                isFavorite = !isFavorite;
                updateHeartIcon();

                prefs.edit().putBoolean(bookKey, isFavorite).apply();

                String message = isFavorite ? "Add to Favorite" : "Remove from Favorite";
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

                // Hiệu ứng nhấn
                v.animate()
                        .scaleX(1.2f)
                        .scaleY(1.2f)
                        .setDuration(150)
                        .withEndAction(() -> v.animate().scaleX(1f).scaleY(1f).setDuration(150))
                        .start();
            });
        }

        return rootView;
    }

    private void updateHeartIcon() {
        if (isFavorite) {
            btnFavorite.setColorFilter(Color.parseColor("#E91E63")); // đỏ hồng
        } else {
            btnFavorite.clearColorFilter(); // về trắng
        }
    }
}
