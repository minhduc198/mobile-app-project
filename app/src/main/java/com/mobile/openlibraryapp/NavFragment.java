package com.mobile.openlibraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
        myBooks.setOnClickListener(v -> {
            openFavouriteFragment();
        });

        browse.setOnClickListener(v -> {
            showDropdown(v);
        });

        return view;
    }
    private void showDropdown(View anchorView) {
        View popupView = getLayoutInflater().inflate(R.layout.browse_menu, null);

        // Create the popup window for browse menu
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // Link for browse
        TextView linkSubjects = popupView.findViewById(R.id.btnSubjects);
        TextView linkTrending = popupView.findViewById(R.id.btnTrending);
        TextView linkLibraryExplorer = popupView.findViewById(R.id.btnLibraryExplorer);
        TextView linkLists = popupView.findViewById(R.id.btnLists);
        TextView linkCollections = popupView.findViewById(R.id.btnCollections);
        TextView linkK12 = popupView.findViewById(R.id.btnK12);
        TextView linkBookTalks = popupView.findViewById(R.id.btnBookTalks);
        TextView linkRandomBook = popupView.findViewById(R.id.btnRandomBook);
        TextView linkAdvancedSearch = popupView.findViewById(R.id.btnAdvancedSearch);

        // Webview for browse
        linkSubjects.setOnClickListener(v -> openWebPage("https://openlibrary.org/subjects"));
        linkTrending.setOnClickListener(v -> openWebPage("https://openlibrary.org/trending/now"));
        linkLibraryExplorer.setOnClickListener(v -> openWebPage("https://openlibrary.org/explore"));
        linkLists.setOnClickListener(v -> openWebPage("https://openlibrary.org/lists"));
        linkCollections.setOnClickListener(v -> openWebPage("https://openlibrary.org/collections"));
        linkK12.setOnClickListener(v -> openWebPage("https://openlibrary.org/collections/k-12"));
        linkBookTalks.setOnClickListener(v -> openWebPage("https://archive.org/details/booktalks"));
        linkRandomBook.setOnClickListener(v -> openWebPage("https://openlibrary.org/random-book"));
        linkAdvancedSearch.setOnClickListener(v -> openWebPage("https://openlibrary.org/advancedsearch"));

        popupWindow.showAsDropDown(anchorView);
    }

    private void openWebPage(String url) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private void openFavouriteFragment() {
        Fragment favouriteFragment = new book.FavouriteBookFragment();

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container1, favouriteFragment) // make sure this ID matches your FrameLayout in activity_main.xml
                .addToBackStack(null)
                .commit();
    }
}
