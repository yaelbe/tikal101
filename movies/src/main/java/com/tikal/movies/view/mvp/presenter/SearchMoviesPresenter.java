package com.tikal.movies.view.mvp.presenter;


import android.text.TextUtils;

import com.tikal.movies.BuildConfig;
import com.tikal.movies.data.entity.Configuration;
import com.tikal.movies.data.entity.Movie;
import com.tikal.movies.data.queries.GetImageConfigurationQuery;
import com.tikal.movies.data.queries.PopularMovieQuery;
import com.tikal.movies.data.queries.SearchMovieQuery;
import com.tikal.movies.view.App;
import com.tikal.movies.view.mapper.MovieMapper;
import com.tikal.movies.view.mvp.BasePresenter;
import com.tikal.movies.view.mvp.view.SearchMoviesView;

import java.util.List;

public class SearchMoviesPresenter implements BasePresenter {

    private SearchMoviesView view;
    private String apiKey;
    private String lastQuery = "";

    public SearchMoviesPresenter(SearchMoviesView view) {
        this.view = view;
        this.apiKey = BuildConfig.API_KEY;
    }

    @Override
    public void createView() {
        hideAllViews();
        view.showLoading();

        checkIfHasTheBaseImageURL();
    }

    @Override
    public void destroyView() {
        view.cleanTimer();
    }

    public void performSearch(String query) {
        if(!TextUtils.isEmpty(query) && !lastQuery.equals(query.trim())) { // avoid blank searches and consecutive repeated searches

            lastQuery = query.trim(); // store the last query

            view.hideView();
            view.showLoading();

            App.JOB_MANAGER.addJobInBackground(new SearchMovieQuery(apiKey, lastQuery, new SearchMovieQuery.SearchMovieQueryCallback() {
                @Override
                public void onMoviesSearched(List<Movie> movieEntities) {
                    view.hideLoading();
                    view.renderMoviesList(new MovieMapper().toModels(movieEntities));
                    view.showView();
                }

                @Override
                public void onError(String reason) {
                    view.hideLoading();
                    view.removeMoviesList();
                    view.showFeedback(reason);
                    lastQuery = "";
                }
            }));
        }
    }

    public void initPopular() {
        String sort = "popularity.desc";
        if(!TextUtils.isEmpty(sort) && !lastQuery.equals(sort.trim())) { // avoid blank searches and consecutive repeated searches

            lastQuery = sort.trim(); // store the last query

            view.hideView();
            view.showLoading();

            App.JOB_MANAGER.addJobInBackground(new PopularMovieQuery(apiKey, lastQuery, new PopularMovieQuery.PopularMovieQueryCallback() {
                @Override
                public void onMoviesSearched(List<Movie> movieEntities) {
                    view.hideLoading();
                    view.renderMoviesList(new MovieMapper().toModels(movieEntities));
                    view.showView();
                }

                @Override
                public void onError(String reason) {
                    view.hideLoading();
                    view.removeMoviesList();
                    view.showFeedback(reason);
                    lastQuery = "";
                }
            }));
        }
    }

    private void hideAllViews() {
        view.hideView();
        view.hideLoading();
    }

    private void checkIfHasTheBaseImageURL() {
        if(!App.LOCAL_DATA.hasBaseImageURL()) {
            App.JOB_MANAGER.addJobInBackground(new GetImageConfigurationQuery(apiKey, new GetImageConfigurationQuery.GetImageConfigurationQueryCallback() {
                @Override
                public void onConfigurationDownloaded(Configuration configuration) {
                    App.LOCAL_DATA.storeBaseImageURL(configuration.getBase_url());

                    showEmptyMovies();
                }

                @Override
                public void onError(String reason) {
                    view.hideLoading();
                    view.showView();
                    view.showFeedback(reason);
                }
            }));

        } else {
            showEmptyMovies();
        }
    }

    private void showEmptyMovies() {
        view.hideLoading();
        view.removeMoviesList();
        view.showView();
    }
}
