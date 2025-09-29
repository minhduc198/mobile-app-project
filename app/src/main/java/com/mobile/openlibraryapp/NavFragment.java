package com.mobile.openlibraryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobile.openlibraryapp.R;

public class NavFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav, container, false);

        TextView myBooks = view.findViewById(R.id.tvMyBooks);
        TextView browse = view.findViewById(R.id.tvBrowse);

        // Need to add more func
//        myBooks.setOnClickListener(v -> {
//        });
//
//        browse.setOnClickListener(v -> {
//        });

        return view;
    }
}
