package com.example.rosie.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTasks { private static RetrofitTasks instance = null;
    private ApIServices myAPIService;

    private RetrofitTasks() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.ADD_TASKS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIService = retrofit.create(ApIServices.class);

    }

    public static synchronized RetrofitTasks getInstance() {
        if (instance == null) {
            instance = new RetrofitTasks();
        }
        return instance;
    }

    public ApIServices getMyApi() {
        return myAPIService;
    }

}
