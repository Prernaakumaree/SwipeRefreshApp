package prerna.com.swiperefreshapp;

import android.app.Application;

import prerna.com.swiperefreshapp.di.AppComponent;
import prerna.com.swiperefreshapp.di.AppModule;
import prerna.com.swiperefreshapp.di.DaggerAppComponent;

public class MainApp extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
            .appModule(new AppModule(this))
            .build();
    }

    public static AppComponent getAppComponent() {
        return component;
    }
}
