package com.mobile.openlibraryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_BOOK = 0;
    private static final int TYPE_FOOTER = 1;

    private List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == bookList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_BOOK;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_BOOK) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            return new BookViewHolder(view);
        } else { // Footer
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_footer, parent, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookViewHolder) {
            Book book = bookList.get(position);
            ((BookViewHolder) holder).title.setText(book.getTitle());
            ((BookViewHolder) holder).author.setText(book.getAuthor());
        }
        // Footer: không cần bind dữ liệu động
    }

    @Override
    public int getItemCount() {
        return bookList.size() + 1; // thêm 1 cho footer
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView title, author;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(android.R.id.text1);
            author = itemView.findViewById(android.R.id.text2);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
