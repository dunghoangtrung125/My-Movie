package com.trungdunghoang125.mymovie.repository;

import androidx.lifecycle.LiveData;

import com.trungdunghoang125.mymovie.remote.model.MovieModel;
import com.trungdunghoang125.mymovie.remote.request.MovieApiClient;

import java.util.List;

/**
 * Created by trungdunghoang125 on 08/10/2022
 */
public class MovieRepository {
    private static MovieRepository instance;
    private LiveData<List<MovieModel>> popMovieList;
    private MovieApiClient movieApiClient;

    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
        popMovieList = movieApiClient.getPopMovieList();
    }

    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    public LiveData<List<MovieModel>> getPopMovieList() {
        return popMovieList;
    }
}
