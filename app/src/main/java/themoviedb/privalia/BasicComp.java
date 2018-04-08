package themoviedb.privalia;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Arturo on 08/04/2018.
 */

@Singleton
@Component(modules = {ApplicationMdbModule.class})
public interface BasicComp {
    void inject(MainActivity mainActivity);
}