package themoviedb.privalia;

import android.app.Application;


/**
 * Created by Arturo on 08/04/2018.
 */

public class ApplicationMdb extends Application {

    private static ApplicationMdb applicationMdb;
    private BasicComp basicComp;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationMdb = this;
        basicComp = DaggerBasicComp.builder()
                .applicationMdbModule(new ApplicationMdbModule(getApplicationContext()))
                .build();
    }

    public static ApplicationMdb app() {
        return applicationMdb;
    }

    public BasicComp basicComp() {
        return basicComp;
    }

}
