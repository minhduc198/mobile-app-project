package com.mobile.openlibraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import book.Book1;
import category.Category;
import category.CategoryAdapter;


import okhttp3.Call;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList = new ArrayList<>();
    private OkHttpClient client = new OkHttpClient();

    private RecyclerView recvCategory;
    private CategoryAdapter categoryAdapter;
    private DrawerLayout drawerLayout;
    private Call currentCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDrawerListener();

        // Khởi tạo Category
        recvCategory = findViewById(R.id.recv_category);
        categoryAdapter = new CategoryAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recvCategory.setLayoutManager(linearLayoutManager);

        categoryAdapter.setData(getListCategory());
        recvCategory.setAdapter(categoryAdapter);

        // Khởi tạo Drawer và RecyclerView cho Book
        drawerLayout = findViewById(R.id.drawerLayout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
    }

    private void setupDrawerListener(){
        // Khởi tạo các TextView trong menu
        TextView itemSubjects = findViewById(R.id.itemSubjects);
        TextView itemTrending = findViewById(R.id.itemTrending);
        TextView itemLibraryExplorer = findViewById(R.id.itemLibraryExplorer);
        TextView itemLists = findViewById(R.id.itemLists);
        TextView itemCollections = findViewById(R.id.itemCollections);
        TextView itemK12 = findViewById(R.id.itemK12);
        TextView itemBookTalks = findViewById(R.id.itemBookTalks);
        TextView itemRandomBook = findViewById(R.id.itemRandomBook);
        TextView itemAdvancedSearch = findViewById(R.id.itemAdvancedSearch);
        TextView itemAddBook = findViewById(R.id.itemAddBook);
        TextView itemRecentEdits = findViewById(R.id.itemRecentEdits);
        TextView itemHelp = findViewById(R.id.itemHelp);
        TextView itemDevCenter = findViewById(R.id.itemDevCenter);

        // Setup click cho các TextView
        itemSubjects.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/subjects");
        });
        itemTrending.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/trending/now");
        });
        itemLibraryExplorer.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/explore");
        });
        itemLists.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/lists");
        });
        itemCollections.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/collections");
        });
        itemK12.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/collections/k-12");
        });
        itemBookTalks.setOnClickListener(v -> {
            openWebPage("https://archive.org/details/booktalks");
        });
        itemRandomBook.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/random-book");
        });
        itemAdvancedSearch.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/advancedsearch");
        });
        itemAddBook.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/add-a-book");
        });
        itemRecentEdits.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/recent-community-edits");
        });
        itemHelp.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/help-support");
        });
        itemDevCenter.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/developer-center");
        });
    }

    private List<Category> getListCategory() {
        List<Category> listCategory = new ArrayList<>();

        List<Book1> listBook = new ArrayList<>();
        listBook.add(new Book1(R.drawable.book,"Read"));
        listBook.add(new Book1(R.drawable.windwillow,"Locate"));
        listBook.add(new Book1(R.drawable.snowhite,"Locate"));
        listBook.add(new Book1(R.drawable.harry,"Read"));
        listBook.add(new Book1(R.drawable.elsa,"Read"));
        listBook.add(new Book1(R.drawable.beautybeaste,"Locate"));
        listBook.add(new Book1(R.drawable.traindragon,"Read"));
        listBook.add(new Book1(R.drawable.harry,"Read"));
        listBook.add(new Book1(R.drawable.elsa,"Locate"));
        listBook.add(new Book1(R.drawable.beautybeaste,"Read"));
        listBook.add(new Book1(R.drawable.traindragon,"Locate"));

        listCategory.add(new Category("Trending Books", listBook));
        listCategory.add(new Category("Classic Books", listBook));
        listCategory.add(new Category("History Books", listBook));
        listCategory.add(new Category("Magic Books", listBook));

        return listCategory;
    }


    public void openRightDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.END);
        }
        HeaderFragment headerFragment = (HeaderFragment)
                getSupportFragmentManager().findFragmentById(R.id.headerFragment);
        if (headerFragment != null) {
            headerFragment.hideSearchBoxIfNeeded();
        }
    }

    public void closeRightDrawer() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            HeaderFragment headerFragment = (HeaderFragment)
                    getSupportFragmentManager().findFragmentById(R.id.headerFragment);
            if (headerFragment != null) {
                View searchBox = headerFragment.getSearchBoxView();
                if (searchBox != null && searchBox.getVisibility() == View.VISIBLE) {
                    int[] loc = new int[2];
                    searchBox.getLocationOnScreen(loc);
                    float x = ev.getRawX();
                    float y = ev.getRawY();

                    android.graphics.Rect rect = new android.graphics.Rect(
                            loc[0],
                            loc[1],
                            loc[0] + searchBox.getWidth(),
                            loc[1] + searchBox.getHeight()
                    );

                    if (!rect.contains((int) x, (int) y)) {
                        headerFragment.hideSearchBoxIfNeeded();
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    private void openWebPage(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
