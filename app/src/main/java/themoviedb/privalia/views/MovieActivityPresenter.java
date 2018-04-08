package themoviedb.privalia.views;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import themoviedb.privalia.models.MoviePageModel;
import themoviedb.privalia.network.MdbService;
import themoviedb.privalia.network.NetworkError;

/**
 * Created by Arturo on 08/04/2018.
 */

public class MovieActivityPresenter {

    private final MovieActivityView view;
    private final MdbService service;
    private CompositeSubscription subscriptions;


    public MovieActivityPresenter(MdbService service,MovieActivityView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }


    public void getMovieList(int page){

        view.showWait();

        Subscription subscription = service.getMovieList(new MdbService.GetMovieListCallback() {
            @Override
            public void onSuccess(MoviePageModel movieListResponse) {
                view.removeWait();
                view.getMovieListSucess(movieListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        },page);

        subscriptions.add(subscription);

    }

    public void searchMovie(int page, String query){
        view.showWait();

        Subscription subscription = service.searchMovie(new MdbService.GetMovieListCallback() {
            @Override
            public void onSuccess(MoviePageModel movieListResponse) {
                view.removeWait();
                view.getMovieListSucess(movieListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        },page,query);

        subscriptions.add(subscription);

    }

    public void onStop() {
        subscriptions.unsubscribe();
    }


}
