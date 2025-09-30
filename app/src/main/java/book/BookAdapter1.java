package book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.openlibraryapp.R;

import java.util.List;

public class BookAdapter1 extends RecyclerView.Adapter<BookAdapter1.BookViewHolder> {

    private List<Book1> books1;

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

        // TODO: Gắn dữ liệu vào view (ví dụ: hiển thị hình ảnh, tiêu đề)
        // holder.imageView.setImageResource(book.getImageResId());
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

            // TODO: Tham chiếu đến view trong item_book1.xml
            // imageView = itemView.findViewById(R.id.imageView);
            // button = itemView.findViewById(R.id.button);
        }
    }
}
