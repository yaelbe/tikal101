package com.tikal.movies.data.service;


import com.tikal.movies.data.entity.MovieDetail;
import com.tikal.movies.data.service.response.GetImageConfiguration;
import com.tikal.movies.data.service.response.GetMovieImages;
import com.tikal.movies.data.service.response.SearchMovie;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public class API {

    public static final String BASE_URL = "http://api.themoviedb.org/3";

    public interface QUERIES {

        //CONFIGURATIONS
        @GET("/configuration")
        void configurations(@Query("api_key") String apiKey, Callback<GetImageConfiguration> callback);

        //MOVIE SEARCH AUTOCOMPLETE
        @GET("/search/movie")
        void search(@Query("api_key") String apiKey, @Query("query") String query, Callback<SearchMovie> callback);

        //MOVIE SEARCH AUTOCOMPLETE
        @GET("/discover/movie")
        void popular(@Query("api_key") String apiKey, @Query("sort") String sort, Callback<SearchMovie> callback);

        //MOVIE DETAIL
        @GET("/movie/{id}")
        void movieDetails(@Query("api_key") String apiKey, @Path("id") int movieID, Callback<MovieDetail> callback);

        //MOVIE IMAGES
        @GET("/movie/{id}/images")
        void movieImages(@Query("api_key") String apiKey, @Path("id") int movieID, Callback<GetMovieImages> callback);
    }

    public static QUERIES http() {
        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(QUERIES.class);
    }

}
