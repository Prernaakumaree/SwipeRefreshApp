package prerna.com.swiperefreshapp.data;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@AutoValue
public abstract class FactsDataModel {
    @SerializedName("title")
    @Nullable
    public abstract String getTitle();

    @SerializedName("rows")
    @Nullable
    public abstract ArrayList<RowModel> getRowDataModels();

    public static TypeAdapter<FactsDataModel> typeAdapter(Gson gson) {
        return new AutoValue_FactsDataModel.GsonTypeAdapter(gson);
    }

    public static FactsDataModel.Builder builder() {
        return new AutoValue_FactsDataModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract FactsDataModel.Builder setTitle(String title);
        public abstract FactsDataModel.Builder setRowDataModels(ArrayList<RowModel> models);
        public abstract FactsDataModel build();
    }
}
