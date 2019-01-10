package prerna.com.swiperefreshapp.main;

import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import prerna.com.swiperefreshapp.data.FactsDataModel;
import prerna.com.swiperefreshapp.data.FactsDataModelSubject;
import prerna.com.swiperefreshapp.network.NetworkClient;

import javax.inject.Inject;

public class MainPresenter {

    private final NetworkClient mNetworkClient;
    private final MainScreen mScreen;
    private final PublishSubject<FactsDataModel> mModelSubject;

    private CompositeDisposable mSubscription = new CompositeDisposable();

    @Inject
    public MainPresenter(MainScreen screen, NetworkClient networkClient,
        @FactsDataModelSubject PublishSubject<FactsDataModel> modelSubject) {
        mNetworkClient = networkClient;
        mScreen = screen;
        mModelSubject = modelSubject;
    }

    public void onScreenCreate() {
        mScreen.hideErrorView();
        mScreen.showLoading();

        mSubscription.add(mModelSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(factsDataModel -> {
                Log.e("zzz" , "facts data size : " + factsDataModel.getRowDataModels().size());
                    mScreen.hideLoading();
                    mScreen.setActionBarTitle(factsDataModel.getTitle());
                    mScreen.setItems(factsDataModel.getRowDataModels());
                },
                this::logError));

        mNetworkClient.getDataFromWeb();
    }

    public void onScreenDestroy() {
        mSubscription.clear();
    }

    private void logError(Throwable throwable) {
        Log.e("TAG" , "Error occuered : " + throwable.getMessage());
        mScreen.hideLoading();
        mScreen.showErrorView();
    }
}