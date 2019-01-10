package prerna.com.swiperefreshapp.main;

import dagger.Subcomponent;

@Subcomponent(modules={ MainModule.class })
public interface MainActivitySubComponent {
    void inject(MainActivity activity);
}