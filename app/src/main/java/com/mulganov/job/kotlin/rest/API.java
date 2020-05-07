package com.mulganov.job.kotlin.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    interface JSONPlaceHolderApi {
        @GET("/api/json/v1/1/filter.php")
        Call<Post> getPostWithID(@Query("c") String c);

        @GET("/api/json/v1/1/list.php?c=list")
        Call<Category> getAllPosts();
    }
}
