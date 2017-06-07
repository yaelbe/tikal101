package com.tikal.movies.view.mvp.view;

import com.tikal.movies.view.mvp.BaseView;

public interface LoadDataView extends BaseView {

    void showLoading();
    void hideLoading();

    void showRetry(String msg);
    void hideRetry();

    void showEmpty(String msg);
    void hideEmpty();
}
