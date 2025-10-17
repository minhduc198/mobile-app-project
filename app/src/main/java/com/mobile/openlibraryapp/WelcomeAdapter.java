package com.mobile.openlibraryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WelcomeAdapter extends RecyclerView.Adapter<WelcomeAdapter.WelcomeViewHolder> {
    private Context mContext;
    private List<Welcome> mListWelcome;

    public WelcomeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Welcome> list){
        this.mListWelcome = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WelcomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_welcome, parent, false);
        return new WelcomeViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mListWelcome != null ? mListWelcome.size() : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull WelcomeViewHolder holder, int position) {
        Welcome welcome = mListWelcome.get(position);
        if (welcome == null) return;

        // Set the image
        holder.imgWelcome.setImageResource(welcome.getImageResource());

        // Set title and description
        if (welcome.getTitle() != null) {
            holder.tvTitle.setText(welcome.getTitle());
        }
        if (welcome.getDescription() != null) {
            holder.tvDescription.setText(welcome.getDescription());
        }
    }

    public class WelcomeViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgWelcome;
        private TextView tvTitle;
        private TextView tvDescription;

        public WelcomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgWelcome = itemView.findViewById(R.id.img_welcome);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}