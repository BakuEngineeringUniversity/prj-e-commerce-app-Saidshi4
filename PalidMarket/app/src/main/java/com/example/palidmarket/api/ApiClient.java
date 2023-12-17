package com.example.palidmarket.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private Retrofit retrofit;

    public Retrofit getClient() {
        OkHttpClient c = new OkHttpClient.Builder().build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.224.17:8787/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(c)
                .build();
        return retrofit;
    }
}
