package prerna.com.swiperefreshapp.network;

import android.net.ConnectivityManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.subjects.PublishSubject;
import prerna.com.swiperefreshapp.data.FactsDataModel;
import prerna.com.swiperefreshapp.data.FactsDataModelAdapterFactory;
import prerna.com.swiperefreshapp.data.FactsDataModelSubject;
import prerna.com.swiperefreshapp.data.RowModel;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

public class NetworkClientImpl implements NetworkClient {
    public static final String TAG = "TAG";

    private final ConnectivityManager mConnectivityManager;
    private final OkHttpClient mOkHttpClient;
    private final Gson mGson;
    private PublishSubject<FactsDataModel> mSubject;

    @Inject
    public NetworkClientImpl(ConnectivityManager connectivityManager,
        OkHttpClient okHttpClient, @FactsDataModelSubject PublishSubject<FactsDataModel> subject) {
        mConnectivityManager = connectivityManager;
        mOkHttpClient  = okHttpClient;
        mSubject = subject;
        mGson = new GsonBuilder()
            .registerTypeAdapterFactory(FactsDataModelAdapterFactory.create())
            .create();
    }

    @Override
    public boolean isConnectionAvailable() {
        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        return mConnectivityManager.getActiveNetworkInfo() != null;
    }

    @Override
    public void getDataFromWeb() {
        Log.e(TAG, " Response data : getDataFromWebApi");

        Request request = new Request.Builder()
            .header("Content-Type", "application/json")
            .url(NetworkConstant.MAIN_URL)
            .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mSubject.onError(new NetworkException("Request Failed : " + e.toString()));
            }

            @Override
            public void onResponse(Response response) throws IOException {

                if (response.isSuccessful()) {
                    try {
                        final String responseJson = response.body().string();
                        JSONObject jObject = new JSONObject(responseJson);
                        JSONArray jArray = jObject.getJSONArray(NetworkConstant.KEY_ROWS);
                        ArrayList<RowModel> rowModels = new ArrayList<>();
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject object = jArray.getJSONObject(i);
                            RowModel rowModel = RowModel.builder()
                                .setTitle(object.getString(NetworkConstant.KEY_TITLE))
                                .setDescription(object.getString(NetworkConstant.KEY_DESCRIPTION))
                                .setImageHref(object.getString(NetworkConstant.KEY_IMAGE_REF))
                                .build();
                            rowModels.add(rowModel);
                        }
                        Log.d(TAG, " Response row size : " + rowModels.size());

                        FactsDataModel model = FactsDataModel.builder()
                            .setTitle(jObject.getString(NetworkConstant.KEY_TITLE))
                            .setRowDataModels(rowModels)
                            .build();
                        Log.e(TAG, " Response data successful");
                        mSubject.onNext(model);
                    } catch (JSONException e) {
                        Log.e(TAG, " Response data unsuccessful >> " + e.getMessage());
                        mSubject.onError(new NetworkException(response.message()));
                    }
                } else {
                    mSubject.onError(new NetworkException(response.message()));
                }
            }
        });
    }

    @Override
    public PublishSubject<FactsDataModel> getObservable() {
        return PublishSubject.create();
    }
}
