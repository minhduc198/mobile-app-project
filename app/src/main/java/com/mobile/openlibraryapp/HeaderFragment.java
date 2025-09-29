package com.mobile.openlibraryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HeaderFragment extends Fragment {

    private ImageView btnMenu, logo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, container, false);


        btnMenu = view.findViewById(R.id.btnMenuHeader);
        logo = view.findViewById(R.id.logo);

        // Bấm icon menu -> mở Drawer menu bên phải
        btnMenu.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).openRightDrawer();
            }
        });

        return view;
    }

    // Public để MainActivity gọi khi click ra ngoài
    public void hideSearchBoxIfNeeded() {
        // Không còn searchBox, nên để trống
    }
}
