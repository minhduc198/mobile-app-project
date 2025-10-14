package book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.openlibraryapp.R;

import java.util.List;

public class BookAdapter1 extends RecyclerView.Adapter<BookAdapter1.BookViewHolder> {

    private Context context;
    private List<Book1> books1;

    public BookAdapter1(Context context) {
        this.context = context;
    }

    public void setData(List<Book1> list) {
        this.books1 = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book1 book = books1.get(position);
        if (book == null) return;
        if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
            String coverUrl = "https://covers.openlibrary.org/b/isbn/" + book.getIsbn() + "-M.jpg";

            Glide.with(context)
                    .load(coverUrl)
                    .placeholder(book.getResId())
                    .error(book.getResId())
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(book.getResId());
        }
        holder.button.setText("READ");

        View.OnClickListener openBookDetail = v -> {
                BookDetailFragment fragment = new BookDetailFragment();

        Bundle args = new Bundle();
        args.putString("book_title", book.getTitle());
        args.putString("book_author", book.getAuthor());
//        args.putInt("book_image", book.getResId());
        args.putString("book_isbn", book.getIsbn());
        args.putString("book_description", book.getDescription());
        args.putInt("book_favorite_count", book.getFavoriteCount());
        args.putInt("book_currently_reading_count", book.getCurrentlyReadingCount());
        args.putInt("book_have_read_count", book.getHaveReadCount());
        fragment.setArguments(args);

        View fragmentContainer = ((FragmentActivity) context).findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {
            fragmentContainer.setVisibility(View.VISIBLE);
        }

        FragmentTransaction transaction = ((FragmentActivity) context)
                .getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
         };

        holder.button.setOnClickListener(openBookDetail);
//        holder.imageView.setOnClickListener(openBookDetail);

    }

    @Override
    public int getItemCount() {
        if (books1 != null) {
            return books1.size();
        }
        return 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button button;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_book);
            button = itemView.findViewById(R.id.button);
        }
    }
}
