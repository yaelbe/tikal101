package com.tikal.movies.view.mvp.view;

import java.util.List;

import com.tikal.movies.view.mvp.model.MovieModel;

public interface SearchMoviesView extends LoadDataView {

    void renderMoviesList(List<MovieModel> movies);
    void removeMoviesList();

    void cleanTimer();
}
