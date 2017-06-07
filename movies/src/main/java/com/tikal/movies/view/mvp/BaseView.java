package com.tikal.movies.view.mvp;

public interface BaseView {

    void showView();
    void hideView();

    void showFeedback(String msg);
    void destroyItself();
}
