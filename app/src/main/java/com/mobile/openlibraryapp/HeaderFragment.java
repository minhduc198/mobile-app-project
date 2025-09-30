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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderFragment extends Fragment {

    private ImageView btnSearch, btnMenu, logo;
    private AutoCompleteTextView searchBox;
    private ArrayAdapter<String> suggestionAdapter;
    private OkHttpClient client;

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

        client = new OkHttpClient();

        suggestionAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                new ArrayList<>()
        );
        searchBox.setAdapter(suggestionAdapter);
        searchBox.setThreshold(1);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 2) {
                    fetchSuggestionsFromApi(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

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

    // Cho MainActivity lấy được searchBox
    public View getSearchBoxView() {
        return searchBox;
    }

    private void fetchSuggestionsFromApi(String query) {
        String url = "https://openlibrary.org/search.json?q=" + Uri.encode(query);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        JSONArray docs = json.getJSONArray("docs");

                        List<String> results = new ArrayList<>();
                        for (int i = 0; i < Math.min(docs.length(), 10); i++) {
                            JSONObject book = docs.getJSONObject(i);
                            if (book.has("title")) {
                                results.add(book.getString("title"));
                            }
                        }

                        requireActivity().runOnUiThread(() -> {
                            suggestionAdapter.clear();
                            suggestionAdapter.addAll(results);
                            suggestionAdapter.notifyDataSetChanged();
                            searchBox.showDropDown();
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
