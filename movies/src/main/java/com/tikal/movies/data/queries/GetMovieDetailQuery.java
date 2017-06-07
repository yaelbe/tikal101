package com.tikal.movies.data.queries;

import com.tikal.movies.data.BaseQuery;
import com.tikal.movies.data.BaseQueryCallback;
import com.tikal.movies.data.entity.MovieDetail;
import com.tikal.movies.data.service.API;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GetMovieDetailQuery extends BaseQuery {

    public interface GetMovieDetailQueryCallback extends BaseQueryCallback {
        void onMovieDetailLoaded(MovieDetail movieDetail);
    }

    private int movieID;
    private String apiKey;

    public GetMovieDetailQuery(String apiKey, int movieID, GetMovieDetailQueryCallback callback) {
        super(callback);
        this.movieID = movieID;
        this.apiKey = apiKey;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().movieDetails(apiKey, movieID, new Callback<MovieDetail>() {
            @Override
            public void success(MovieDetail movieDetail, Response response) {
                ((GetMovieDetailQueryCallback)callback).onMovieDetailLoaded(movieDetail);
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
