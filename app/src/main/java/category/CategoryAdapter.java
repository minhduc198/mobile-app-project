package category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.openlibraryapp.R;

import org.jspecify.annotations.NonNull;

import java.util.List;

import book.BookAdapter1;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context mContext;
    private List<Category> categoryL;

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Category> list) {
        this.categoryL = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder,int position) {
        Category category = categoryL.get(position);
        if(category == null) {
            return;
        }

        holder.nameCate.setText(category.getNameCate());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false);
        holder.recvBook.setLayoutManager(linearLayoutManager);

        BookAdapter1 bookAdapter = new BookAdapter1();
        bookAdapter.setData(category.getBooks());
        holder.recvBook.setAdapter(bookAdapter);
    }

    @Override
    public int getItemCount() {
        if(categoryL != null) {
            return categoryL.size();
        }
        return 0;
    }
    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView nameCate;
        private RecyclerView recvBook;
        public CategoryViewHolder(View itemView) {
            super(itemView);

            nameCate = itemView.findViewById(R.id.name_cate);
            recvBook = itemView.findViewById(R.id.recv_book);
        }
    }
}
