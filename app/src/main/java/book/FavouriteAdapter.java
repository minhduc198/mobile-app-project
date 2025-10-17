package book;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile.openlibraryapp.R;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private Context context;
    private List<Favourite> favourBooks;

    public FavouriteAdapter(Context context) { this.context = context;}

    public void setData(List<Favourite> list) {
        this.favourBooks = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouriteAdapter.FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_layout, parent, false);
        return new FavouriteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.FavouriteViewHolder holder, int position) {
        Favourite book = favourBooks.get(position);
        if (book == null) return;

        holder.imageView.setImageResource(book.getImgFavour());
        holder.tvTitle.setText(book.getTitle());

        View.OnClickListener openDetail = v -> {
            BookDetailFragment fragment = new BookDetailFragment();

            Bundle args = new Bundle();
            args.putString("book_title", book.getTitle());
            args.putString("book_author", book.getAuthor());
            args.putString("book_isbn", book.getIsbn());
            args.putString("book_description", book.getDescription());
            args.putInt("book_image", book.getImgFavour());
            args.putInt("book_favorite_count", book.getFavoriteCount());
            args.putInt("book_currently_reading_count", book.getCurrentlyReadingCount());
            args.putInt("book_have_read_count", book.getHaveReadCount());
            fragment.setArguments(args);

            FragmentTransaction transaction = ((FragmentActivity) context)
                    .getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.fragment_container1, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        };

        holder.tvTitle.setOnClickListener(openDetail);
        holder.imageView.setOnClickListener(openDetail);

    }

    @Override
    public int getItemCount() {
        if (favourBooks != null) {
            return favourBooks.size();
        }
        return 0;
    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView tvTitle;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_favour);
            tvTitle = itemView.findViewById(R.id.txt_favour);
        }
    }
}
