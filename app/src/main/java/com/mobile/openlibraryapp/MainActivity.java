package com.mobile.openlibraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.view.GravityCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SearchView;
import android.widget.Toast;

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

    private SearchView searchView;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList = new ArrayList<>();
    private OkHttpClient client = new OkHttpClient();
    private DrawerLayout drawerLayout; // DrawerLayout để mở/đóng menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchBooks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 2) {
                    fetchBooks(newText);
                }
                return false;
            }
        });
    }

    // Hàm public để HeaderFragment gọi mở Drawer
    public void openRightDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.END);
        }
    }

    public void closeRightDrawer() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    // ---------------- API ----------------
    public void fetchBooks(String query) {
        try {
            query = java.net.URLEncoder.encode(query, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = "https://openlibrary.org/search.json?q=" + query;
        Log.d("API_CALL", "Fetching: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
                );
                Log.e("API_ERROR", "Request failed", e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e("API_ERROR", "Response error or empty body");
                    return;
                }

                try {
                    String bodyString = response.body().string();
                    Log.d("API_RESULT", "Raw JSON (first 300): " +
                            bodyString.substring(0, Math.min(300, bodyString.length())));

                    JSONObject jsonObject = new JSONObject(bodyString);
                    JSONArray docs = jsonObject.optJSONArray("docs");

                    bookList.clear();
                    if (docs != null) {
                        for (int i = 0; i < docs.length(); i++) {
                            JSONObject bookObj = docs.optJSONObject(i);
                            if (bookObj == null) continue;

                            String title = bookObj.optString("title", "Không có tiêu đề");
                            JSONArray authors = bookObj.optJSONArray("author_name");
                            String author = (authors != null && authors.length() > 0)
                                    ? authors.optString(0, "Không rõ")
                                    : "Không rõ";

                            bookList.add(new Book(title, author));
                        }
                    }

                    runOnUiThread(() -> {
                        Log.d("UI_UPDATE", "Updating RecyclerView with " + bookList.size() + " items");
                        adapter.notifyDataSetChanged();
                    });


                } catch (Exception e) {
                    Log.e("API_ERROR", "Parse error", e);
                } finally {
                    response.close();
                }
            }
        });
    }


    // ---------------- Ẩn search box khi click ra ngoài ----------------
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            HeaderFragment headerFragment = (HeaderFragment)
                    getSupportFragmentManager().findFragmentById(R.id.headerFragment);
            if (headerFragment != null) {
                headerFragment.hideSearchBoxIfNeeded();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
