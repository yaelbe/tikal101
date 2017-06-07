package com.tikal.movies.view.ui.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tikal.movies.R;
import com.tikal.movies.view.ui.BaseActivity;
import com.tikal.movies.view.ui.fragment.SearchMoviesFragment;

public class SearchMoviesActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_with_toolbar;
    }

    @Override
    public Fragment getMainFragment() {
        return SearchMoviesFragment.newInstance();
    }
}
