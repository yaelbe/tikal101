package com.tikal.movies.data.queries;


import com.tikal.movies.data.BaseQuery;
import com.tikal.movies.data.BaseQueryCallback;
import com.tikal.movies.data.entity.Movie;
import com.tikal.movies.data.service.API;
import com.tikal.movies.data.service.response.SearchMovie;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PopularMovieQuery extends BaseQuery {

    public interface PopularMovieQueryCallback extends BaseQueryCallback {
        void onMoviesSearched(List<Movie> movieEntities);
    }

    private String apiKey;
    private String sort;

    public PopularMovieQuery(String apiKey, String sort, PopularMovieQueryCallback callback) {
        super(callback);
        this.apiKey = apiKey;
        this.sort = sort;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().popular(apiKey, sort, new Callback<SearchMovie>() {
            @Override
            public void success(SearchMovie searchMovie, Response response) {
                ((PopularMovieQueryCallback) callback).onMoviesSearched(searchMovie.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getKind() == RetrofitError.Kind.NETWORK) {
                    errorReason = NETWORK_ERROR;
                } else {
                    errorReason = error.getResponse().getReason();
                }
                onCancel();
            }
        });
    }
}
