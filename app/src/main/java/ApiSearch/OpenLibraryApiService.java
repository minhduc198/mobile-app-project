package ApiSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ApiSearch.BookSearchResponse;

public interface OpenLibraryApiService {

    @GET("search.json")
    Call<BookSearchResponse> searchBooks(@Query("q") String query);
}