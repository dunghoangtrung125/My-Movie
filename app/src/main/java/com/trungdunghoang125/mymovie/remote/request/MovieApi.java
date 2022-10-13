package com.trungdunghoang125.mymovie.remote.request;

import com.trungdunghoang125.mymovie.utils.Constant;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by trungdunghoang125 on 07/10/2022
 */
public class MovieApi {
    private static final OkHttpClient client = new OkHttpClient();

    public Call getPopMovie() {
        HttpUrl url = new HttpUrl.Builder().scheme("https").host(Constant.HOST_NAME).addPathSegment("3").addPathSegment("movie").addPathSegment("popular").addQueryParameter("api_key", Constant.API_KEY).build();
        Request request = new Request.Builder().url(url).build();
        return client.newCall(request);
    }

    public Call searchMovieByName(String name) {
        HttpUrl url = new HttpUrl.Builder().scheme("https").host(Constant.HOST_NAME).addPathSegment("3").addPathSegment("search").addPathSegment("movie").addQueryParameter("api_key", Constant.API_KEY).addQueryParameter("query", name).addQueryParameter("page", "1").build();
        Request request = new Request.Builder().url(url).build();
        return client.newCall(request);
    }
}
