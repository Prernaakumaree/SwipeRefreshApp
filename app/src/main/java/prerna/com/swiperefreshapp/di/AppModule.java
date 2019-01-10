package prerna.com.swiperefreshapp.di;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import prerna.com.swiperefreshapp.network.NetworkModule;

@Module (includes = {NetworkModule.class})
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    Context providesAppContext() {
        return application;
    }
}
