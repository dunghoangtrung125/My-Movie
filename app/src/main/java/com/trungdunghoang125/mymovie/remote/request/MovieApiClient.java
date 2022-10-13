package com.trungdunghoang125.mymovie.remote.request;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trungdunghoang125.mymovie.remote.model.MovieModel;
import com.trungdunghoang125.mymovie.remote.response.MovieResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;

/**
 * Created by trungdunghoang125 on 08/10/2022
 */
public class MovieApiClient {
    private static MovieApiClient instance;
    private final MutableLiveData<List<MovieModel>> popMovieList;
    private final MutableLiveData<List<MovieModel>> searchMovieList;

    private MovieApiClient() {
        popMovieList = new MutableLiveData<>();
        searchMovieList = new MutableLiveData<>();
    }

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private void getPopMovie() {
        new GetPopMovie().execute();
    }

    public LiveData<List<MovieModel>> getPopMovieList() {
        getPopMovie();
        return popMovieList;
    }

    private void searchMovieByName(String name) {
        new SearchMovieByName().execute(name);
    }

    public LiveData<List<MovieModel>> getSearchMovieList(String name) {
        searchMovieByName(name);
        return searchMovieList;
    }

    private class GetPopMovie extends AsyncTask<Void, Void, List<MovieModel>> {
        private List<MovieModel> resultList;

        @Override
        protected List<MovieModel> doInBackground(Void... voids) {
            MovieApi api = new MovieApi();
            try {
                Response response = api.getPopMovie().execute();
                Gson gson = new Gson();
                MovieResponse movieResponse = gson.fromJson(response.body().string(), MovieResponse.class);
                resultList = movieResponse.getMovieList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultList;
        }

        @Override
        protected void onPostExecute(List<MovieModel> movieModels) {
            super.onPostExecute(movieModels);
            popMovieList.postValue(movieModels);
        }
    }

    private class SearchMovieByName extends AsyncTask<String, Void, List<MovieModel>> {
        private List<MovieModel> resultList;

        @Override
        protected List<MovieModel> doInBackground(String... strings) {
            String name = strings[0];
            MovieApi api = new MovieApi();
            try {
                Response response = api.searchMovieByName(name).execute();
                Gson gson = new GsonBuilder().create();
                MovieResponse movieResponse = gson.fromJson(response.body().string(), MovieResponse.class);
                resultList = movieResponse.getMovieList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultList;
        }

        @Override
        protected void onPostExecute(List<MovieModel> movieModels) {
            super.onPostExecute(movieModels);
            searchMovieList.postValue(movieModels);
        }
    }
}