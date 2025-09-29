package book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.openlibraryapp.R;

import org.jspecify.annotations.NonNull;

import java.util.List;

public class BookAdapter1 extends RecyclerView.Adapter<BookAdapter1.BookViewHolder> {

    private List<Book1> booksL;
    public void setData(List<Book1> list) {
        this.booksL = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int postion) {
        Book1 book = booksL.get(postion);
        if(book == null) {
            return;
        }

        holder.imgBook.setImageResource(book.getResId());
        holder.actionView.setText(book.getActionView());
    }

    @Override
    public int getItemCount() {
        if(booksL != null) {
            return booksL.size();
        }
        return 0;
    }
    public class BookViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgBook;
        private Button actionView;
        public BookViewHolder(View itemView) {
            super(itemView);

            imgBook = itemView.findViewById(R.id.img_book);
            actionView = itemView.findViewById(R.id.title_book);
        }
    }
}
