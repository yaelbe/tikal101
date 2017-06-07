package com.tikal.movies.view.mvp.presenter;

import android.text.TextUtils;

import com.tikal.movies.BuildConfig;
import com.tikal.movies.data.entity.MovieDetail;
import com.tikal.movies.data.queries.GetMovieDetailQuery;
import com.tikal.movies.view.App;
import com.tikal.movies.view.mapper.MovieMapper;
import com.tikal.movies.view.mvp.BasePresenter;
import com.tikal.movies.view.mvp.model.MovieModel;
import com.tikal.movies.view.mvp.view.MovieDetailView;

public class MovieDetailPresenter implements BasePresenter {

    private MovieDetailView view;
    private MovieModel movieModel;
    private String apiKey;

    public MovieDetailPresenter(MovieDetailView view, MovieModel movieModel) {
        this.view = view;
        this.movieModel = movieModel;
        this.apiKey = BuildConfig.API_KEY;
    }

    @Override
    public void createView() {
        hideAllViews();
        view.showLoading();

        downloadMovieDetails();
    }

    @Override
    public void destroyView() {}

    public void onHomepageClicked() {
        if(!TextUtils.isEmpty(movieModel.getHomepage())) {
            view.openMovieWebsite(movieModel.getHomepage());
        }
    }

    public void onGalleryClicked() {
        view.openGallery();
    }

    public void onMainViewScrolled() {
        view.updateToolbarColor();
    }

    private void hideAllViews() {
        view.hideView();
        view.hideLoading();
        view.hideRetry();
        view.hideEmpty();
    }

    private void downloadMovieDetails() {
        App.JOB_MANAGER.addJobInBackground(new GetMovieDetailQuery(apiKey, movieModel.getId(), new GetMovieDetailQuery.GetMovieDetailQueryCallback() {
            @Override
            public void onMovieDetailLoaded(MovieDetail movieDetail) {
                updateMovieModel(movieDetail);

                view.updateBackground(movieModel.getBigCover());
                view.updateTitle(movieModel.getName());

                if (TextUtils.isEmpty(movieModel.getYearOfRelease())) {
                    view.hideYearOfRelease();
                } else {
                    view.updateYearOfRelease(movieModel.getYearOfRelease());
                }

                if (TextUtils.isEmpty(movieModel.getHomepage())) {
                    view.hideHomepage();
                } else {
                    view.updateHomepage(movieModel.getHomepage());
                }

                if (movieModel.getCompanies().isEmpty()) {
                    view.hideCompanies();
                } else {
                    view.updateCompanies(movieModel.getCompanies());
                }

                if (TextUtils.isEmpty(movieModel.getTagline())) {
                    view.hideTagline();
                } else {
                    view.updateTagline(movieModel.getTagline());
                }

                if (TextUtils.isEmpty(movieModel.getOverview())) {
                    view.hideOverview();
                } else {
                    view.updateOverview(movieModel.getOverview());
                }

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

    private void updateMovieModel(MovieDetail detailEntity) {
        movieModel = new MovieMapper().addDetails(movieModel, detailEntity);
    }
}
