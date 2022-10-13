package com.trungdunghoang125.mymovie.ui.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.trungdunghoang125.mymovie.R;
import com.trungdunghoang125.mymovie.databinding.ActivityMovieListBinding;
import com.trungdunghoang125.mymovie.remote.model.MovieModel;
import com.trungdunghoang125.mymovie.ui.adapter.ItemClick;
import com.trungdunghoang125.mymovie.ui.adapter.MovieListAdapter;
import com.trungdunghoang125.mymovie.ui.viewModel.MovieListViewModel;
import com.trungdunghoang125.mymovie.utils.Constant;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements ItemClick {
    private ActivityMovieListBinding binding;
    private RecyclerView rcViewPopMovie;
    private SearchView searchView;
    private MovieListViewModel viewModel;
    private MovieListAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rcViewPopMovie = binding.rcMovieList;
        searchView = binding.searchView;
        // set support for action bar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        // view model get instance
        viewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        // set adapter for recycler view
        configureRecyclerView();
        // get Pop movie list
        getPopMovie();
        setUpSearchView();
    }

    private void getPopMovie() {
        viewModel.searchPopMovie();
        observerPopMovie();
    }

    private void observerMovieList() {
        viewModel.getSearchMovieList().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels != null) {
                    adapter.setMovieModelList(movieModels);
                }
            }
        });
    }

    private void setUpSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchMovieByName(query);
                observerMovieList();
                Constant.IS_POPULAR = false;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void configureRecyclerView() {
        adapter = new MovieListAdapter(this);
        rcViewPopMovie.setAdapter(adapter);
    }

    private void observerPopMovie() {
        viewModel.getPopMovieList().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels != null) {
                    adapter.setMovieModelList(movieModels);
                }
            }
        });
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Constant.INTENT_KEY, adapter.getSelectedMovie(pos));
        startActivity(intent);
    }
}