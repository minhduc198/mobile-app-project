package com.mobile.openlibraryapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).openRightDrawer();
            }
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

        // Hiện bàn phím
        InputMethodManager imm = (InputMethodManager) requireContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(searchBox, InputMethodManager.SHOW_IMPLICIT);
        }
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

        // Ẩn bàn phím
        InputMethodManager imm = (InputMethodManager) requireContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
        }
    }

    // Public để MainActivity gọi khi click ra ngoài
    public void hideSearchBoxIfNeeded() {
        if (searchBox != null && searchBox.getVisibility() == View.VISIBLE) {
            hideSearchBox();
        }
    }
}
