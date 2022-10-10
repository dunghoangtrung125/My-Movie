package com.trungdunghoang125.mymovie.remote.request;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
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
    private MutableLiveData<List<MovieModel>> popMovieList;

    private MovieApiClient() {
        popMovieList = new MutableLiveData<>();
        new GetPopMovie().execute();
    }

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    public LiveData<List<MovieModel>> getPopMovieList() {
        return popMovieList;
    }

    private class GetPopMovie extends AsyncTask<Void, Void, List<MovieModel>> {
        private List<MovieModel> movieList;

        @Override
        protected List<MovieModel> doInBackground(Void... voids) {
            MovieApi api = new MovieApi();
            try {
                Response response = api.getPopMovie().execute();
                Gson gson = new Gson();
                MovieResponse movieResponse = gson.fromJson(response.body().string(), MovieResponse.class);
                movieList = movieResponse.getMovieList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieList;
        }

        @Override
        protected void onPostExecute(List<MovieModel> movieModels) {
            super.onPostExecute(movieModels);
            popMovieList.postValue(movieModels);
        }
    }
}