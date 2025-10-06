package com.mobile.openlibraryapp;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiManager {

    private static final OkHttpClient client = new OkHttpClient();

    // Phương thức gọi API tìm kiếm sách
    public static void fetchBooks(String query, final ApiCallback callback) {
        if (query == null || query.trim().isEmpty()) {
            callback.onFailure(new IOException("Query cannot be empty"));
            return;
        }

        final String url = "https://openlibrary.org/search.json?q=" + query.replace(" ", "+");

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure(new IOException("Unexpected code " + response));
                    return;
                }

                try {
                    String bodyString = response.body().string();
                    response.close();

                    // Phân tích kết quả từ API
                    JSONObject jsonObject = new JSONObject(bodyString);
                    JSONArray docs = jsonObject.optJSONArray("docs");

                    List<Book> books = new ArrayList<>();
                    if (docs != null) {
                        for (int i = 0; i < docs.length(); i++) {
                            JSONObject bookObj = docs.getJSONObject(i);
                            String title = bookObj.optString("title", "Không có tiêu đề");
                            JSONArray authors = bookObj.optJSONArray("author_name");
                            String author = (authors != null && authors.length() > 0)
                                    ? authors.getString(0)
                                    : "Không rõ";

                            books.add(new Book(title, author));
                        }
                    }

                    callback.onSuccess(books);

                } catch (Exception e) {
                    callback.onFailure(e);
                }
            }
        });
    }

    // Interface callback để trả kết quả về
    public interface ApiCallback {
        void onFailure(Exception e);
        void onSuccess(List<Book> books);
    }
}
