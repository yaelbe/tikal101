package com.tikal.movies.view.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tikal.movies.R;
import com.tikal.movies.view.mapper.MovieMapper;
import com.tikal.movies.view.mvp.model.MovieModel;
import com.tikal.movies.view.mvp.presenter.MovieListPresenter;
import com.tikal.movies.view.mvp.view.MovieListView;
import com.tikal.movies.view.ui.BaseFragment;
import com.tikal.movies.view.ui.adapter.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MovieListFragment extends BaseFragment implements MovieListView {

    public static final String TAG = MovieListFragment.class.getSimpleName();
    public static final String MOVIES = TAG + ".MOVIES";

    private RecyclerView recyclerView;
    private View loadingView, retryView, emptyView;
    private TextView retryMsg, emptyMsg;
    private Button retryButton;

    private MoviesAdapter adapter;
    private MovieListPresenter presenter;

    private List<MovieModel> movies;

    public static MovieListFragment newInstance(List<MovieModel> movies) {

        MovieListFragment fragment = new MovieListFragment();

        Bundle args = new Bundle();
        args.putStringArrayList(MOVIES, (ArrayList<String>) new MovieMapper().serializeModels(movies));

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle args = getArguments();

        if(args != null) {
            movies = new MovieMapper().deserializeModels(args.getStringArrayList(MOVIES));
        }else{

        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void instantiatePresenter() {
        presenter = new MovieListPresenter(this, movies);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void initializePresenter() {
        presenter.createView();
    }

    @Override
    public void finalizePresenter() {
        presenter.destroyView();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_recycler;
    }

    @Override
    public void mapGUI(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        loadingView = view.findViewById(R.id.view_loading);
        emptyView = view.findViewById(R.id.view_empty);
        retryView = view.findViewById(R.id.view_retry);

        emptyMsg = (TextView) emptyView.findViewById(R.id.text);
        retryMsg = (TextView) retryView.findViewById(R.id.text);

        retryButton = (Button) retryView.findViewById(R.id.button);
    }

    @Override
    public void configureGUI() {

        //RECYLCER VIEW CONFIGURATIONS
        adapter = new MoviesAdapter(getActivity());
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(adapter);
        //RECYLCER VIEW CONFIGURATIONS


        //RETRY BUTTON CONFIGURATIONS
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createView();
            }
        });
        //RETRY BUTTON CONFIGURATIONS

    }

    @Override
    public void renderMovies(List<MovieModel> movies) {
        adapter.setData(movies);
    }

    @Override
    public void clearMovies() {
        adapter.clearData();
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showRetry(String msg) {
        retryMsg.setText(msg);
        retryView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        retryView.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty(String msg) {
        emptyMsg.setText(msg);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void showView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showFeedback(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void destroyItself() {
        getActivity().finish();
    }
}
