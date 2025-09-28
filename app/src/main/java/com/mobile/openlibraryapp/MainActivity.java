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

        // Ánh xạ DrawerLayout (phải có trong activity_main.xml)
        drawerLayout = findViewById(R.id.drawerLayout);

        // Ánh xạ view tìm sách
//        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);

        // Bắt sự kiện Search
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                fetchBooks(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (newText.length() > 2) {
//                    fetchBooks(newText);
//                }
//                return false;
//            }
//        });
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
    private void fetchBooks(String query) {
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
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e("API_ERROR", "Unexpected code " + response);
                    return;
                }

                try {
                    String bodyString = response.body().string();
                    Log.d("API_RESULT", "Raw JSON: " + bodyString);

                    JSONObject jsonObject = new JSONObject(bodyString);
                    JSONArray docs = jsonObject.optJSONArray("docs");

                    bookList.clear();
                    if (docs != null) {
                        for (int i = 0; i < docs.length(); i++) {
                            JSONObject bookObj = docs.getJSONObject(i);
                            String title = bookObj.optString("title", "Không có tiêu đề");
                            JSONArray authors = bookObj.optJSONArray("author_name");
                            String author = (authors != null && authors.length() > 0)
                                    ? authors.getString(0)
                                    : "Không rõ";

                            bookList.add(new Book(title, author));
                        }
                    }

                    runOnUiThread(() -> {
                        Log.d("API_RESULT", "Số sách tìm thấy: " + bookList.size());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,
                                "Tìm thấy " + bookList.size() + " sách",
                                Toast.LENGTH_SHORT).show();
                    });

                } catch (Exception e) {
                    Log.e("API_ERROR", "Parse error", e);
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
