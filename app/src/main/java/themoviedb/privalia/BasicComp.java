package themoviedb.privalia;

import javax.inject.Singleton;

import dagger.Component;
import themoviedb.privalia.views.MovieActivity;

/**
 * Created by Arturo on 08/04/2018.
 */

@Singleton
@Component(modules = {ApplicationMdbModule.class})
public interface BasicComp {
    void inject(MovieActivity movieActivity);
}