package com.example.rosie.apis;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDelete {

	private static RetrofitDelete instance = null;
    private ApIServices myAPIService;

    private RetrofitDelete() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.DELETE_USER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIService = retrofit.create(ApIServices.class);

    }

    public static synchronized RetrofitDelete getInstance() {
        if (instance == null) {
            instance = new RetrofitDelete();
        }
        return instance;
    }

    public ApIServices getMyApi() {
        return myAPIService;
    }

}
