package prerna.com.swiperefreshapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import prerna.com.swiperefreshapp.R;
import prerna.com.swiperefreshapp.data.RowModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {

    private static final String TAG = DataAdapter.class.getSimpleName();

    private List<RowModel> mItems;
    private final Context mContext;

    @Inject
    public DataAdapter(Context context) {

        this.mContext = context;
        this.mItems = new ArrayList<>();
    }

    public class DataHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail) ImageView mThumbnail;
        @BindView(R.id.title) TextView mTitle;
        @BindView(R.id.description) TextView mDescription;

        public DataHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mItemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_row, parent, false);

        return new DataHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {

        final RowModel mItem = mItems.get(position);

        if (mItem != null)
        {

            if (mItem.getTitle().equals("null")) {
                holder.mTitle.setText(R.string.default_heading);
            } else {
                holder.mTitle.setText(mItem.getTitle());
            }

            if (mItem.getDescription().equals("null")) {
                holder.mDescription.setText(R.string.default_description);
            } else {
                holder.mDescription.setText(mItem.getDescription());
            }

            Picasso.with(mContext).load(mItem.getImageHref())
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.mThumbnail);
        }

        //holder.mContainer.setOnClickListener(view -> mListener.onItemClick(mItem));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<RowModel> mItems) {

        if (mItems != null && !mItems.isEmpty()) {

            this.mItems = mItems;
            notifyDataSetChanged();
        }

    }
}


