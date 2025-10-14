package ApiSearch;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookSearchResponse {

    @SerializedName("docs")
    private List<Book> docs;

    public List<Book> getDocs() {
        return docs;
    }
}