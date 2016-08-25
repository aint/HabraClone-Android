package com.github.aint.habraclone.android.rest;

import com.github.aint.habraclone.android.rest.service.HabraCloneService;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HabraCloneRestClient {
    public static final String BASE_URL = "http://192.168.0.100:9090/api/";
//    public static final String BASE_URL = "http://10.0.2.2:9090/api/";
//    public static final String BASE_URL = "http://192.168.43.47:9090/api/";

    private static HabraCloneService instance;

    public static HabraCloneService getInstance() {
        if (instance == null) {
            instance = createRetrofit().create(HabraCloneService.class);
        }
        return instance;
    }

    private static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(BASE_URL)
                .build();
    }

}
