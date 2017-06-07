package com.tikal.movies.data.service.response;

import com.tikal.movies.data.entity.Movie;

import java.util.List;

public class SearchMovie {
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
