package themoviedb.privalia.network;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import themoviedb.privalia.models.MoviePageModel;

/**
 * Created by Arturo on 08/04/2018.
 */

public class MdbService {
    private final MdbEndpointInterface networkService;

    public MdbService(MdbEndpointInterface networkService) {
        this.networkService = networkService;
    }

    public Subscription getMovieList(final GetMovieListCallback callback,int page) {

        return networkService.getPopularMoviesList(page, MdbStatics.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MoviePageModel>>() {
                    @Override
                    public Observable<? extends MoviePageModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<MoviePageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(MoviePageModel movieListResponse) {
                        callback.onSuccess(movieListResponse);

                    }
                });
    }

    public Subscription searchMovie(final GetMovieListCallback callback,int page, String query) {

        return networkService.searchMovies(page,query, MdbStatics.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MoviePageModel>>() {
                    @Override
                    public Observable<? extends MoviePageModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<MoviePageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(MoviePageModel movieListResponse) {
                        callback.onSuccess(movieListResponse);
                    }
                });
    }



    public interface GetMovieListCallback{
        void onSuccess(MoviePageModel moviePageModel);
        void onError(NetworkError networkError);
    }
}