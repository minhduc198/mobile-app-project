package com.mobile.openlibraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.view.GravityCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import book.Book1;
import category.Category;
import category.CategoryAdapter;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recvCategory;
    private CategoryAdapter categoryAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList = new ArrayList<>();
    private OkHttpClient client = new OkHttpClient();

    private DrawerLayout drawerLayout;
    private Call currentCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recvCategory = findViewById(R.id.recv_category);
        categoryAdapter = new CategoryAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recvCategory.setLayoutManager(linearLayoutManager);

        categoryAdapter.setData(getListCategory());
        recvCategory.setAdapter(categoryAdapter);
    }

    private List<Category> getListCategory() {
        List<Category>listCategory = new ArrayList<>();

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

        listCategory.add(new Category("Trending Books",listBook));
        listCategory.add(new Category("Classic Books",listBook));
        listCategory.add(new Category("History Books",listBook));
        listCategory.add(new Category("Magic Books",listBook));
        return listCategory;

        drawerLayout = findViewById(R.id.drawerLayout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
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

    // -Call API
    public synchronized void fetchBooks(final String query) {
        if (query == null) return;
        final String trimmed = query.trim();
        if (trimmed.isEmpty()) {
            runOnUiThread(() -> {
                bookList.clear();
                adapter.notifyDataSetChanged();
            });
            return;
        }

        final String url = "https://openlibrary.org/search.json?q=" + trimmed.replace(" ", "+");
        Log.d(TAG, "Fetching: " + url);

        if (currentCall != null && !currentCall.isCanceled()) {
            currentCall.cancel();
        }

        Request request = new Request.Builder().url(url).build();

        currentCall = client.newCall(request);
        currentCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (call.isCanceled()) {
                    Log.d(TAG, "Request cancelled: " + trimmed);
                    return;
                }
                Log.e(TAG, "Request failed", e);
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Unexpected code " + response);
                    response.close();
                    return;
                }

                try {
                    String bodyString = response.body().string();
                    response.close();

                    JSONObject jsonObject = new JSONObject(bodyString);
                    JSONArray docs = jsonObject.optJSONArray("docs");

                    final List<Book> newList = new ArrayList<>();
                    if (docs != null) {
                        for (int i = 0; i < docs.length(); i++) {
                            JSONObject bookObj = docs.getJSONObject(i);
                            String title = bookObj.optString("title", "Không có tiêu đề");
                            JSONArray authors = bookObj.optJSONArray("author_name");
                            String author = (authors != null && authors.length() > 0)
                                    ? authors.getString(0)
                                    : "Không rõ";

                            newList.add(new Book(title, author));
                        }
                    }

                    runOnUiThread(() -> {
                        bookList.clear();
                        bookList.addAll(newList);
                        adapter.notifyDataSetChanged();
                    });

                } catch (Exception e) {
                    Log.e(TAG, "Parse error", e);
                }
            }
        });

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
}
