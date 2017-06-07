package com.tikal.movies.data.queries;

import com.tikal.movies.data.BaseQuery;
import com.tikal.movies.data.BaseQueryCallback;
import com.tikal.movies.data.entity.Image;
import com.tikal.movies.data.service.API;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GetMovieImagesQuery extends BaseQuery {

    public interface GetMovieImagesQueryCallback extends BaseQueryCallback {
        void onImagesUrlsLoaded(List<Image> backdrops, List<Image> posters);
    }

    private String apiKey;
    private int movieID;

    public GetMovieImagesQuery(String apiKey, int movieID, GetMovieImagesQueryCallback callback) {
        super(callback);
        this.movieID = movieID;
        this.apiKey = apiKey;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().movieImages(apiKey, movieID, new Callback<com.tikal.movies.data.service.response.GetMovieImages>() {
            @Override
            public void success(com.tikal.movies.data.service.response.GetMovieImages getMovieImages, Response response) {
                ((GetMovieImagesQueryCallback)callback).onImagesUrlsLoaded(getMovieImages.getBackdrops(), getMovieImages.getPosters());
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
