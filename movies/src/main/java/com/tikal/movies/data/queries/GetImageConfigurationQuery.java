package com.tikal.movies.data.queries;

import com.tikal.movies.data.BaseQuery;
import com.tikal.movies.data.BaseQueryCallback;
import com.tikal.movies.data.entity.Configuration;
import com.tikal.movies.data.service.API;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GetImageConfigurationQuery extends BaseQuery {

    public interface GetImageConfigurationQueryCallback extends BaseQueryCallback {
        void onConfigurationDownloaded(Configuration configuration);
    }

    private String apiKey;

    public GetImageConfigurationQuery(String apiKey, GetImageConfigurationQueryCallback callback) {
        super(callback);
        this.apiKey = apiKey;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().configurations(apiKey, new Callback<com.tikal.movies.data.service.response.GetImageConfiguration>() {
            @Override
            public void success(com.tikal.movies.data.service.response.GetImageConfiguration getImageConfiguration, Response response) {
                ((GetImageConfigurationQueryCallback) callback).onConfigurationDownloaded(getImageConfiguration.getImages());
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
