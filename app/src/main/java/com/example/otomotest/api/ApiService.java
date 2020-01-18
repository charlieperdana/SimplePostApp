package com.example.otomotest.api;

import com.example.otomotest.model.Owner;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("post")
    Call<Owner> getAllPost();

    @GET("post/{id}")
    Call<Owner> searchPost(@Path("id") int postId);
}
