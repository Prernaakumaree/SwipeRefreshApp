package prerna.com.swiperefreshapp.network;

import android.content.Context;
import android.net.ConnectivityManager;

import com.squareup.okhttp.OkHttpClient;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;
import prerna.com.swiperefreshapp.data.FactsDataModel;
import prerna.com.swiperefreshapp.data.FactsDataModelSubject;

import javax.inject.Singleton;

@Module
public class NetworkModule {
    @Provides
    ConnectivityManager providesConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    OkHttpClient providesOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    NetworkClient providesNetworkClient(ConnectivityManager manager, OkHttpClient client,
        @FactsDataModelSubject PublishSubject<FactsDataModel> model) {
        return new NetworkClientImpl(manager, client, model);
    }

    @Provides
    @FactsDataModelSubject
    @Singleton
    PublishSubject<FactsDataModel> providesFactsDataModel() {
        return PublishSubject.create();
    }
}
