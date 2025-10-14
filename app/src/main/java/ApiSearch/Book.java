package ApiSearch;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class Book {

    @SerializedName("title")
    private String title;

    @SerializedName("author_name")
    private List<String> authorName;

    @SerializedName("first_publish_year")
    private Integer firstPublishYear;

    @SerializedName("cover_i")
    private Long coverId;

    // Getters for all the fields

    public String getTitle() {
        return title;
    }

    public List<String> getAuthorName() {
        return authorName;
    }

    public Integer getFirstPublishYear() {
        return firstPublishYear;
    }

    public Long getCoverId() {
        return coverId;
    }
}
