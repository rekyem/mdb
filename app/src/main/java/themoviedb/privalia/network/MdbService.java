package themoviedb.privalia.network;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MdbService {
    private final MdbEndpointInterface networkService;

    public MdbService(MdbEndpointInterface networkService) {
        this.networkService = networkService;
    }

    public Subscription getMovieList(final GetMovieListCallback callback,int page) {

        return networkService.getMostPopularMoviesList(page, MdbStatics.API_KEY, MdbStatics.SORT_BY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MdbResponse>>() {
                    @Override
                    public Observable<? extends MdbResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<MdbResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(MdbResponse movieListResponse) {
                        callback.onSuccess(movieListResponse);

                    }
                });
    }

    public interface GetMovieListCallback{
        void onSuccess(MdbResponse movieListResponse);
        void onError(NetworkError networkError);
    }
}