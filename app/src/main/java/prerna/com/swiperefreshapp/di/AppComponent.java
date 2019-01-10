package prerna.com.swiperefreshapp.di;

import dagger.Component;
import prerna.com.swiperefreshapp.main.MainActivitySubComponent;
import prerna.com.swiperefreshapp.main.MainModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    //void inject(MainActivity activity);

    // factory method to instantiate the sub component defined here (passing in the module instance)
    MainActivitySubComponent newMainActivitySubcomponent(MainModule activityModule);
}
