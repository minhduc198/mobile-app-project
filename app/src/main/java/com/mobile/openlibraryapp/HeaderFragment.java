package com.mobile.openlibraryapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HeaderFragment extends Fragment {

    private ImageView btnSearch, btnMenu, logo;
    private AutoCompleteTextView searchBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, container, false);

        btnSearch = view.findViewById(R.id.btnSearch);
        btnMenu = view.findViewById(R.id.btnMenu);
        logo = view.findViewById(R.id.logo);
        searchBox = view.findViewById(R.id.searchBox);

        btnSearch.setOnClickListener(v -> showSearchBox());

        btnMenu.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).openRightDrawer();
            }
        });

        return view;
    }

    private void showSearchBox() {
        btnSearch.setVisibility(View.GONE);
        logo.setVisibility(View.GONE);
        searchBox.setVisibility(View.VISIBLE);
        searchBox.requestFocus();

        searchBox.setAlpha(0f);
        searchBox.animate().alpha(1f).setDuration(200).start();

        InputMethodManager imm = (InputMethodManager) requireContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.showSoftInput(searchBox, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideSearchBox() {
        searchBox.setVisibility(View.GONE);
        logo.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.VISIBLE);

        InputMethodManager imm = (InputMethodManager) requireContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
    }

    public void hideSearchBoxIfNeeded() {
        if (searchBox != null && searchBox.getVisibility() == View.VISIBLE) {
            hideSearchBox();
        }
    }

    // MainActivity to get searchBox
    public View getSearchBoxView() {
        return searchBox;
    }

}
