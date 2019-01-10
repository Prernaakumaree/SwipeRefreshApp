package prerna.com.swiperefreshapp.main;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
@Singleton
public class MainModule {

    private final MainActivity mActivity;

    MainModule(MainActivity activity) {
        mActivity = activity;
    }

    @Provides
    Activity providesActivity() {
        return mActivity;
    }

    @Provides
    MainScreen providesMainScreen() {
        return mActivity;
    }

}
