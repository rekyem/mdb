package themoviedb.privalia.network;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;
import themoviedb.privalia.models.MoviePageModel;

/**
 * Created by Arturo on 08/04/2018.
 */

public interface  MdbEndpointInterface {

    String MdbHeader = "Content-Type: application/json;charset=utf-8";

    @Headers({MdbHeader})
    @GET("movie/popular")
    Observable<MoviePageModel> getPopularMoviesList(@Query("page") Integer page, @Query("api_key") String apiKey);

    @Headers({MdbHeader})
    @GET("search/movie")
    Observable<MoviePageModel> searchMovies(@Query("page") Integer page,@Query("query") String query, @Query("api_key") String apiKey);




}
