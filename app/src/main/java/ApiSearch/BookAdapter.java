package ApiSearch;

import android.content.Context;
import android.text.TextUtils; // <-- IMPORT THIS
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import com.mobile.openlibraryapp.R;
import ApiSearch.Book;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;
    private final Context context;

    public BookAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.title.setText(book.getTitle());

        // Set author names
        if (book.getAuthorName() != null && !book.getAuthorName().isEmpty()) {
            holder.author.setText(TextUtils.join(", ", book.getAuthorName()));
        } else {
            holder.author.setText("Unknown Author");
        }

        // Set publication year
        if (book.getFirstPublishYear() != null) {
            holder.year.setText("First published: " + book.getFirstPublishYear());
        } else {
            holder.year.setText("Unknown Year");
        }

        // Load cover image using Glide
        if (book.getCoverId() != null) {
            String imageUrl = "https://covers.openlibrary.org/b/id/" + book.getCoverId() + "-M.jpg";
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.color.background_beige)
                    .error(R.color.background_beige)
                    .into(holder.cover);
        } else {
            holder.cover.setImageResource(R.color.background_beige);
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public void updateBooks(List<Book> newBooks) {
        this.books.clear();
        this.books.addAll(newBooks);
        notifyDataSetChanged();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView title;
        TextView author;
        TextView year;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.bookCoverImageView);
            title = itemView.findViewById(R.id.bookTitleTextView);
            author = itemView.findViewById(R.id.bookAuthorTextView);
            year = itemView.findViewById(R.id.bookYearTextView);
        }
    }
}
