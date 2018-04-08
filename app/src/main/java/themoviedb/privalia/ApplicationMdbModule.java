package themoviedb.privalia;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import themoviedb.privalia.network.MdbEndpointInterface;
import themoviedb.privalia.network.MdbService;
import themoviedb.privalia.network.MdbStatics;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Arturo on 08/04/2018.
 */

@Module
public class ApplicationMdbModule {

    private Context context;

    public ApplicationMdbModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return context;
    }


    @Provides
    @Singleton
    MdbEndpointInterface mdbEndpointInterface() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MdbStatics.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(MdbEndpointInterface.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public MdbService providesService(
            MdbEndpointInterface mdbEndpointInterface) {
        return new MdbService(mdbEndpointInterface);
    }
}
