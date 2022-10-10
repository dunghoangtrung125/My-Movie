package com.trungdunghoang125.mymovie.ui.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.trungdunghoang125.mymovie.databinding.ActivityMovieDetailBinding;
import com.trungdunghoang125.mymovie.remote.model.MovieModel;
import com.trungdunghoang125.mymovie.utils.Constant;

public class MovieDetailActivity extends AppCompatActivity {
    private ActivityMovieDetailBinding binding;
    private ImageView moviePoster, movieTrailer;
    private RatingBar movieRating;
    private TextView movieTitle, movieOverview;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        moviePoster = binding.moviePosterDetail;
        movieTrailer = binding.movieTrailer;
        movieRating = binding.movieRatingDetail;
        movieTitle = binding.movieTitleDetail;
        movieOverview = binding.movieOverviewDetail;

        getData();
    }

    private void getData() {
        if (getIntent().hasExtra(Constant.INTENT_KEY)) {
            MovieModel movieModel = getIntent().getParcelableExtra(Constant.INTENT_KEY);
            movieTitle.setText(movieModel.getTitle());
            movieRating.setRating(movieModel.getVoteAverage() / 2);
            movieOverview.setText(movieModel.getMovieOverview());
            Glide.with(this).load(Constant.BASE_IMG_URL + movieModel.getPosterPath()).into(moviePoster);
            movieId = movieModel.getMovieId();
        }
    }
}