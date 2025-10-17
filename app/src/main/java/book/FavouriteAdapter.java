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

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>{
    private Context context;
    private List<Favourite> favourBooks;

    public FavouriteAdapter(Context context) { this.context = context;}

    public void setData(List<Favourite> list) {
        this.favourBooks = list;
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

        if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
            String coverUrl = "https://covers.openlibrary.org/b/isbn/" + book.getImgFavour() + "-M.jpg";

            Glide.with(context)
                    .load(coverUrl)
                    .placeholder(book.getImgFavour())
                    .error(book.getImgFavour())
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(book.getImgFavour());
        }
        holder.tvTitle.setText(book.getTitle());

        View.OnClickListener openFavouriteBook = v -> {
            FavouriteBookFragment fragment = new FavouriteBookFragment();

            Bundle args = new Bundle();
            args.putString("book_title", book.getTitle());
            args.putString("book_isbn", book.getIsbn());
            fragment.setArguments(args);

            View fragmentContainer = ((FragmentActivity) context).findViewById(R.id.fragment_container1);
            if (fragmentContainer != null) {
                fragmentContainer.setVisibility(View.VISIBLE);
            }

            FragmentTransaction transaction = ((FragmentActivity) context)
                    .getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.fragment_container1,fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        };

        holder.tvTitle.setOnClickListener(openFavouriteBook);

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
