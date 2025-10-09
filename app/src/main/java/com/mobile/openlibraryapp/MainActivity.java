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
import book.BookAdapter1;
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

        //Connect fragment BookDetail in MainActivity
        BookAdapter1 adapter = new BookAdapter1(MainActivity.this);
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

        // Category 1: Trending Books
        List<Book1> trendingBooks = new ArrayList<>();
        trendingBooks.add(new Book1("Shanna", "Kathleen E. Woodiwiss",R.drawable.book, "Read", "0861883861"));
        trendingBooks.add(new Book1("Rowan of Rin", "Emily Rodda", R.drawable.book, "Read", "0439385652"));
        trendingBooks.add(new Book1("Amber Brown Goes Fourth", "Paula Danziger", R.drawable.book, "Read", "0590934252"));
        trendingBooks.add(new Book1("Mixing with Your Mind", "Michael Paul Stavrou", R.drawable.book, "Read", "0646428756"));
        trendingBooks.add(new Book1("Lehninger Principles of Biochemistry", "David L. Nelson and Michael M. Cox", R.drawable.book, "Read", "9781319228002"));

        // Category 2: Classic Books
        List<Book1> classicBooks = new ArrayList<>();
        classicBooks.add(new Book1("Black Beauty", "Anna Sewell", R.drawable.book, "Read", "9781453054765"));
        classicBooks.add(new Book1("The twins at St. Clare's", "Enid Blyton", R.drawable.book, "Read", "1405219777"));
        classicBooks.add(new Book1("The Faerie Qveene", "Edmund Spenser", R.drawable.book, "Read", "058209951X"));
        classicBooks.add(new Book1("The Red and the Black", "Stendhal", R.drawable.book, "Read", "1840221275"));
        classicBooks.add(new Book1("The History of Herodotus", "Herodotus", R.drawable.book, "Read", "140430732X"));

        // Category 3: Fantasy Books
        List<Book1> fantasyBooks = new ArrayList<>();
        fantasyBooks.add(new Book1("The Lord Of The Rings", " J.R.R. Tolkien", R.drawable.book, "Read", "9780544003415"));
        fantasyBooks.add(new Book1("A Game of Thrones", "George R. R. Martin", R.drawable.book, "Read", "9780553103540"));
        fantasyBooks.add(new Book1("The Way of Kings", "Brandon Sanderson", R.drawable.book, "Read", "9780765326355"));
        fantasyBooks.add(new Book1("The Mark of Athena", "Rick Riordan", R.drawable.book, "Read", "9780545782814"));
        fantasyBooks.add(new Book1("Hatching Magic", "Ann E. Downer", R.drawable.book, "Read", "0689870574"));

        // Category 4: Sci-Fi Books
        List<Book1> scifiBooks = new ArrayList<>();
        scifiBooks.add(new Book1("The Biology of Science Fiction Cinema", "Mark C. Glassy", R.drawable.book, "Read", "0786409983"));
        scifiBooks.add(new Book1("Escape to Witch Mountain", "Alexander Key", R.drawable.book, "Read", "9780671297107"));
        scifiBooks.add(new Book1("The Black Hole", "Alan Dean Foster", R.drawable.book, "Read", "0345285387"));
        scifiBooks.add(new Book1("Enemy Territory", "Keith R. A. DeCandido", R.drawable.book, "Read", "1416500146"));
        scifiBooks.add(new Book1("Wolf Tower", "Tanith Lee", R.drawable.book, "Read", "0142300306"));

        listCategory.add(new Category("Trending Books", trendingBooks));
        listCategory.add(new Category("Classic Books", classicBooks));
        listCategory.add(new Category("Fantasy Books", fantasyBooks));
        listCategory.add(new Category("Sci-Fi Books", scifiBooks));

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
