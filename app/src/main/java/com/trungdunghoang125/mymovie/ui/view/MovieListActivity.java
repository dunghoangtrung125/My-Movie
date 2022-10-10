package com.trungdunghoang125.mymovie.ui.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.trungdunghoang125.mymovie.R;
import com.trungdunghoang125.mymovie.databinding.ActivityMovieListBinding;
import com.trungdunghoang125.mymovie.ui.adapter.ItemClick;
import com.trungdunghoang125.mymovie.ui.adapter.MovieListAdapter;
import com.trungdunghoang125.mymovie.ui.viewModel.MovieListViewModel;
import com.trungdunghoang125.mymovie.utils.Constant;

public class MovieListActivity extends AppCompatActivity implements ItemClick {
    private ActivityMovieListBinding binding;
    private RecyclerView rcViewPopMovie;
    private MovieListViewModel viewModel;
    private MovieListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rcViewPopMovie = binding.rcMovieList;
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
    }

    private void configureRecyclerView() {
        adapter = new MovieListAdapter(this);
        rcViewPopMovie.setAdapter(adapter);
    }

    private void getPopMovie() {
        viewModel.getPopMovieList().observe(this, movieList -> {
            adapter.setMovieModelList(movieList);
        });
    }

    @Override
    public void onItemClick(int pos) {
        Log.d("TAG", "onItemClick: " + pos);
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Constant.INTENT_KEY, adapter.getSelectedMovie(pos));
        startActivity(intent);
    }
}