package com.tikal.movies.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tikal.movies.R;
import com.tikal.movies.view.ui.BaseActivity;
import com.tikal.movies.view.ui.fragment.LoadingImageFragment;

public class ImageActivity extends BaseActivity {

    public static final String TAG = ImageActivity.class.getSimpleName();
    public static final String URL = TAG + ".URL";

    private String url;

    public static Intent getCallingIntent(Context context, String url) {
        Intent intentToBeCalled = new Intent(context, ImageActivity.class);
        intentToBeCalled.putExtra(URL, url);

        return intentToBeCalled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle args = getIntent().getExtras();

        if(args != null) {
            url = args.getString(URL);
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_without_toolbar;
    }

    @Override
    public Fragment getMainFragment() {
        return LoadingImageFragment.newInstance(url);
    }
}
