package com.example.rosie.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSignIn { private static RetrofitSignIn instance = null;
    private ApIServices myAPIService;

    private RetrofitSignIn() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.SIGN_IN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIService = retrofit.create(ApIServices.class);

    }

    public static synchronized RetrofitSignIn getInstance() {
        if (instance == null) {
            instance = new RetrofitSignIn();
        }
        return instance;
    }

    public ApIServices getMyApi() {
        return myAPIService;
    }

}
