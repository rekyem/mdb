package themoviedb.privalia.network;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Arturo on 08/04/2018.
 */

public interface  MdbEndpointInterface {

    final String MdbGet = "4/list/{page}";
    final String MdbHeader = "Content-Type: application/json;charset=utf-8";

    @GET(MdbGet)
    @Headers({MdbHeader})
    Observable<MdbResponse> getMostPopularMoviesList(@Path("page") int page, @Query("api_key") String apiKey, @Query("sort_by") String sortBy);

    //Call<ListHeader> getMostPopularMoviesList(@Path("page") int page, @Query("api_key") String apiKey, @Query("sort_by") String sortBy);

}
