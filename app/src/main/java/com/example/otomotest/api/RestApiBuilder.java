package com.example.otomotest.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiBuilder {
    public static Retrofit retrofit;
    public static final String BASE_URL = "https://n161.tech/api/dummyapi/";

    public RestApiBuilder() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public ApiService getService() {
        return retrofit.create(ApiService.class);
    }
}
