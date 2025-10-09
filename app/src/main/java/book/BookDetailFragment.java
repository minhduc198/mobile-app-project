package book;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobile.openlibraryapp.R;
import com.ramotion.foldingcell.FoldingCell;

public class BookDetailFragment extends Fragment {

    private ImageView imgBook;
    private TextView txtTitle, txtAuthor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_detail, container, false);

        FoldingCell foldingCell = rootView.findViewById(R.id.folding_cell);
        imgBook = rootView.findViewById(R.id.img_book);
        txtTitle = rootView.findViewById(R.id.title);
        txtAuthor = rootView.findViewById(R.id.author);
        foldingCell.setOnClickListener(v -> foldingCell.toggle(false));

        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString("book_title", "Unknown Title");
            String author = args.getString("book_author", "Unknown Author");
            int imageRes = args.getInt("book_image", R.drawable.book);
            String isbn = args.getString("book_isbn", "");

            txtTitle.setText(title);
            txtAuthor.setText(author);

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