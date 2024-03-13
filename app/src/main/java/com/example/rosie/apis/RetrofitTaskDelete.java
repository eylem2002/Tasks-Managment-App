package com.example.rosie.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTaskDelete {
    private static RetrofitTaskDelete instance = null;
    private ApIServices myAPIService;

    private RetrofitTaskDelete() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.DELETE_TASK)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIService = retrofit.create(ApIServices.class);

    }

    public static synchronized RetrofitTaskDelete getInstance() {
        if (instance == null) {
            instance = new RetrofitTaskDelete();
        }
        return instance;
    }

    public ApIServices getMyApi() {
        return myAPIService;
    }
}
