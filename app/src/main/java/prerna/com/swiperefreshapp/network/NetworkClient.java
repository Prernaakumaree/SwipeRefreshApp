package prerna.com.swiperefreshapp.network;

import io.reactivex.subjects.PublishSubject;
import prerna.com.swiperefreshapp.data.FactsDataModel;

public interface NetworkClient {
    boolean isConnectionAvailable();
    void getDataFromWeb();
    PublishSubject<FactsDataModel> getObservable();
}
