package com.tikal.movies.view.mvp.presenter;

import java.util.List;

import com.tikal.movies.view.mvp.BasePresenter;
import com.tikal.movies.view.mvp.model.ImageModel;
import com.tikal.movies.view.mvp.view.GalleryView;

public class GalleryPresenter implements BasePresenter {

    private GalleryView view;
    private List<ImageModel> urls;

    public GalleryPresenter(GalleryView view, List<ImageModel> urls) {
        this.urls = urls;
        this.view = view;
    }

    @Override
    public void createView() {
        hideAllViews();

        view.showLoading();

        if(urls.isEmpty()) {
            view.showEmpty("There are no images.");
            view.hideLoading();
        } else {
            view.renderImages(urls);
            view.hideLoading();
            view.showView();
        }
    }

    @Override
    public void destroyView() {

    }

    private void hideAllViews() {
        view.hideView();
        view.hideLoading();
        view.hideRetry();
        view.hideEmpty();
    }
}
