package com.tikal.movies.data.queries;


import com.tikal.movies.data.BaseQuery;
import com.tikal.movies.data.BaseQueryCallback;
import com.tikal.movies.data.entity.Movie;
import com.tikal.movies.data.service.API;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchMovieQuery extends BaseQuery {

    public interface SearchMovieQueryCallback extends BaseQueryCallback {
        void onMoviesSearched(List<Movie> movieEntities);
    }

    private String apiKey;
    private String query;

    public SearchMovieQuery(String apiKey, String query, SearchMovieQueryCallback callback) {
        super(callback);
        this.apiKey = apiKey;
        this.query = query;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().search(apiKey, query, new Callback<com.tikal.movies.data.service.response.SearchMovie>() {
            @Override
            public void success(com.tikal.movies.data.service.response.SearchMovie searchMovie, Response response) {
                ((SearchMovieQueryCallback) callback).onMoviesSearched(searchMovie.getResults());
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
