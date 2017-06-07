package com.tikal.movies.view.mvp.view;

import java.util.List;

import com.tikal.movies.view.mvp.model.MovieModel;

public interface MovieListView extends LoadDataView {

    void renderMovies(List<MovieModel> movies);
    void clearMovies();

}
