package prerna.com.swiperefreshapp.main;

import prerna.com.swiperefreshapp.data.RowModel;

import java.util.List;

public interface MainScreen {
    void showLoading();
    void hideLoading();
    void showErrorView();
    void hideErrorView();
    void setItems(List<RowModel> mItems);
    void setActionBarTitle(String title);
}
