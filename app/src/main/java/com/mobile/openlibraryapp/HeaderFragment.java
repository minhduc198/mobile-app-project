package com.mobile.openlibraryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HeaderFragment extends Fragment {

    private ImageView btnSearch, btnMenu, logo;
    private EditText searchBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, container, false);

        // Ánh xạ view
        btnSearch = view.findViewById(R.id.btnSearch);
        btnMenu = view.findViewById(R.id.btnMenu);
        logo = view.findViewById(R.id.logo);
        searchBox = view.findViewById(R.id.searchBox);

        // Bấm icon search
        btnSearch.setOnClickListener(v -> showSearchBox());

        // Bấm icon menu -> mở Drawer menu bên phải
        btnMenu.setOnClickListener(v -> {
            if (getActivity() instanceof HeaderActivity) {
                HeaderActivity.openRightMenu(); // gọi hàm static trong HeaderActivity
            }
        });

        // Bấm ra ngoài để đóng search box
        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (searchBox.getVisibility() == View.VISIBLE) {
                    hideSearchBox();
                    return true;
                }
            }
            return false;
        });

        return view;
    }

    // ---------------- SEARCH BOX ----------------
    private void showSearchBox() {
        btnSearch.setVisibility(View.GONE);
        logo.setVisibility(View.GONE);
        searchBox.setVisibility(View.VISIBLE);
        searchBox.requestFocus();

        // Animation search box trượt ra
        searchBox.setAlpha(0f);
        searchBox.setTranslationX(50);
        searchBox.animate()
                .alpha(1f)
                .translationX(0)
                .setDuration(300)
                .start();

        // Khi mất focus (click ra ngoài) -> đóng search
        searchBox.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideSearchBox();
            }
        });
    }

    private void hideSearchBox() {
        searchBox.setVisibility(View.GONE);
        logo.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.VISIBLE);

        // Animation logo hiện lại
        logo.setAlpha(0f);
        logo.setTranslationX(-50);
        logo.animate()
                .alpha(1f)
                .translationX(0)
                .setDuration(300)
                .start();
    }
}
