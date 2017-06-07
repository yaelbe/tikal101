package com.tikal.movies.view.mvp.presenter;

import com.tikal.movies.BuildConfig;
import com.tikal.movies.data.entity.Image;
import com.tikal.movies.data.queries.GetMovieImagesQuery;
import com.tikal.movies.view.App;
import com.tikal.movies.view.mapper.ImageMapper;
import com.tikal.movies.view.mvp.BasePresenter;
import com.tikal.movies.view.mvp.view.MovieImagesView;

import java.util.List;

public class MovieImagesPresenter implements BasePresenter {

    private MovieImagesView view;
    private int movieID;

    public MovieImagesPresenter(MovieImagesView view, int movieID) {
        this.movieID = movieID;
        this.view = view;
    }

    @Override
    public void createView() {
        hideAllViews();

        view.showLoading();

        downloadMovieImages();
    }

    @Override
    public void destroyView() {}

    private void hideAllViews() {
        view.hideView();
        view.hideEmpty();
        view.hideLoading();
        view.hideRetry();
    }

    private void downloadMovieImages() {
        App.JOB_MANAGER.addJobInBackground(new GetMovieImagesQuery(BuildConfig.API_KEY, movieID, new GetMovieImagesQuery.GetMovieImagesQueryCallback() {
            @Override
            public void onImagesUrlsLoaded(List<Image> backdrops, List<Image> posters) {
                view.renderTabs(new ImageMapper("w780").toModels(backdrops), new ImageMapper("w500").toModels(posters));
                view.hideLoading();
                view.showView();
            }

            @Override
            public void onError(String reason) {
                view.showFeedback(reason);
                view.destroyItself();
            }
        }));
    }
}
