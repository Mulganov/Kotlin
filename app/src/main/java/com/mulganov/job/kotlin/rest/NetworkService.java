package com.mulganov.job.kotlin.rest;

import com.mulganov.job.kotlin.Load;
import com.mulganov.job.kotlin.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://www.thecocktaildb.com";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public API.JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(API.JSONPlaceHolderApi.class);
    }

    public void getList(final Load load, final String category){
        Call<Post> a = NetworkService.getInstance()
                .getJSONApi()
                .getPostWithID(category);

        a.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                System.out.println("REST onResponse");
                load.getPost(response.body(), category);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                System.out.println("REST onFailure");
            }
        });
    }

    public void getListCategory(@NotNull final Load load) {
        Call<Category> a = NetworkService.getInstance()
                .getJSONApi()
                .getAllPosts();

        a.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                System.out.println("getListCategory + onResponse");
                load.setCategory(response.body());
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                System.out.println("getListCategory + onFailure");
            }
        });
    }
}