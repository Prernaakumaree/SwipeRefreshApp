package prerna.com.swiperefreshapp.data;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
public abstract class FactsDataModelAdapterFactory implements TypeAdapterFactory {

    public static FactsDataModelAdapterFactory create() {
        return new AutoValueGson_FactsDataModelAdapterFactory();
    }
}
