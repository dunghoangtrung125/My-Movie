package com.trungdunghoang125.mymovie.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.trungdunghoang125.mymovie.remote.model.MovieModel;
import com.trungdunghoang125.mymovie.repository.MovieRepository;

import java.util.List;

/**
 * Created by trungdunghoang125 on 08/10/2022
 */
public class MovieListViewModel extends AndroidViewModel {
    private MovieRepository repository;
    private LiveData<List<MovieModel>> popMovieList;
    private LiveData<List<MovieModel>> searchMovieList;

    public MovieListViewModel(@NonNull Application application) {
        super(application);
        this.repository = MovieRepository.getInstance();
        popMovieList = new MutableLiveData<>();
        searchMovieList = new MutableLiveData<>();
    }

    public void searchPopMovie() {
        popMovieList = repository.getPopMovieList();
    }

    public LiveData<List<MovieModel>> getPopMovieList() {
        return popMovieList;
    }

    public void searchMovieByName(String name) {
        searchMovieList = repository.searchMovieByName(name);
    }

    public LiveData<List<MovieModel>> getSearchMovieList() {
        return searchMovieList;
    }
}
