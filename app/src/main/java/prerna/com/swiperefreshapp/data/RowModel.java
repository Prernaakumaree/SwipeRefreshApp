package prerna.com.swiperefreshapp.data;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class RowModel {

    @SerializedName("title")
    @Nullable
    public abstract String getTitle();

    @SerializedName("description")
    @Nullable
    public abstract String getDescription();

    @SerializedName("imageHref")
    @Nullable
    public abstract String getImageHref();

    public static TypeAdapter<RowModel> typeAdapter(Gson gson) {
        return new AutoValue_RowModel.GsonTypeAdapter(gson);
    }

    public static RowModel.Builder builder() {
        return new AutoValue_RowModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTitle(String title);
        public abstract Builder setDescription(String description);
        public abstract Builder setImageHref(String imageHref);
        public abstract RowModel build();
    }
}
