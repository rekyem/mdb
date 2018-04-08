package themoviedb.privalia.views;

import themoviedb.privalia.models.MoviePageModel;

/**
 * Created by Arturo on 08/04/2018.
 */

public interface MovieActivityView {
        void showWait();
        void removeWait();
        void onFailure(String appErrorMessage);
        void getMovieListSucess(MoviePageModel moviePageModel);
}
