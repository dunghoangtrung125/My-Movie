package com.trungdunghoang125.mymovie.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trungdunghoang125.mymovie.remote.model.MovieModel;

import java.util.List;

/**
 * Created by trungdunghoang125 on 07/10/2022
 */
public class MovieResponse {
    @SerializedName("results")
    @Expose
    private List<MovieModel> mMovieList;

    public List<MovieModel> getMovieList() {
        return mMovieList;
    }
}