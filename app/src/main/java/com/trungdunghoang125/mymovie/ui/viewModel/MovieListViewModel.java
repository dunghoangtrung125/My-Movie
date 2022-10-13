package com.trungdunghoang125.mymovie.ui.viewModel;

import android.app.Application;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.trungdunghoang125.mymovie.remote.model.MovieModel;
import com.trungdunghoang125.mymovie.repository.MovieRepository;
import com.trungdunghoang125.mymovie.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trungdunghoang125 on 08/10/2022
 */
public class MovieListViewModel extends AndroidViewModel implements Filterable {
    private MovieRepository repository;
    private LiveData<List<MovieModel>> popMovieList;
    private LiveData<List<MovieModel>> searchMovieList;
    private List<MovieModel> resultList;
    private MutableLiveData<List<MovieModel>> liveDataFilterList;

    public MovieListViewModel(@NonNull Application application) {
        super(application);
        this.repository = MovieRepository.getInstance();
        popMovieList = new MutableLiveData<>();
        searchMovieList = new MutableLiveData<>();
        liveDataFilterList = new MutableLiveData<>();
        resultList = new ArrayList<>();
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

    public LiveData<List<MovieModel>> getFilterResultList() {
        return liveDataFilterList;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<MovieModel> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    if (Constant.IS_POPULAR) {
                        filteredList.addAll(popMovieList.getValue());
                    }
                    if (!Constant.IS_POPULAR) {
                        filteredList.addAll(searchMovieList.getValue());
                    }
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    if (Constant.IS_POPULAR) {
                        for (MovieModel movie : popMovieList.getValue()) {
                            if (movie.getTitle().toLowerCase().contains(filterPattern)) {
                                filteredList.add(movie);
                            }
                        }
                    }
                    if (!Constant.IS_POPULAR) {
                        for (MovieModel movie : searchMovieList.getValue()) {
                            if (movie.getTitle().toLowerCase().contains(filterPattern)) {
                                filteredList.add(movie);
                            }
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                resultList.clear();
                resultList.addAll((List) results.values);
                liveDataFilterList.setValue(resultList);
            }
        };
    }
}