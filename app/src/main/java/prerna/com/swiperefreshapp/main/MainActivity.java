package prerna.com.swiperefreshapp.main;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import prerna.com.swiperefreshapp.MainApp;
import prerna.com.swiperefreshapp.R;
import prerna.com.swiperefreshapp.adapter.DataAdapter;
import prerna.com.swiperefreshapp.data.RowModel;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainScreen {

    @Inject MainPresenter mainPresenter;
    DataAdapter mAdapter;

    @BindView(R.id.recyclerview) RecyclerView mRecyclerview;
    @BindView(R.id.container) RelativeLayout mContainer;
    @BindView(R.id.content_error) RelativeLayout mErrorLayout;
    @BindView(R.id.swiperefreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;

    private ProgressDialog mProgressDialog;
    private ActionBar mAb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MainApp.getAppComponent().newMainActivitySubcomponent(
            new MainModule(this)).inject(this);

        mAdapter = new DataAdapter(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerview.setAdapter(mAdapter);

        mainPresenter.onScreenCreate();

        // Setup refresh listener which triggers new data loading
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // Your code to refresh the list here.
            // Make sure you call mSwipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            mainPresenter.onScreenCreate();
        });

        // Configure the refreshing colors
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

        mAb = getSupportActionBar();
        mAb.setTitle(R.string.app_name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onScreenDestroy();
    }
    @Override
    public void showLoading() {
        mProgressDialog = showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void setItems(List<RowModel> mItems) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setItems(mItems);
    }

    @Override
    public void setActionBarTitle(String title) {
        mAb.setTitle(title);
    }

    @Override
    public void showErrorView() {
        mContainer.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorView() {
        mErrorLayout.setVisibility(View.GONE);
        mContainer.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.repeat_btn)
    void onRepeatButtonClick() {
        mainPresenter.onScreenCreate();
    }

    private ProgressDialog showLoadingDialog() {

        ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();

        if (mProgressDialog.getWindow() != null)
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mProgressDialog.setContentView(R.layout.progress_dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);

        return mProgressDialog;
    }
}
