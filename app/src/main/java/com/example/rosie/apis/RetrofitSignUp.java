package com.example.rosie.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSignUp { private static RetrofitSignUp instance = null;
    private ApIServices myAPIService;

    private RetrofitSignUp() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.SIGN_UP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIService = retrofit.create(ApIServices.class);

    }

    public static synchronized RetrofitSignUp getInstance() {
        if (instance == null) {
            instance = new RetrofitSignUp();
        }
        return instance;
    }

    public ApIServices getMyApi() {
        return myAPIService;
    }

}
