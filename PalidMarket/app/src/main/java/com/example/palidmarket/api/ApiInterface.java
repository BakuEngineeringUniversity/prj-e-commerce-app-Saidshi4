package com.example.palidmarket.api;

import com.example.palidmarket.entities.Category;
import com.example.palidmarket.entities.User;

import java.util.List;

import kotlin.collections.AbstractMutableList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("users/save")
    Call<User> saveUser(@Body User user);

    @GET("categories/getAll")
    Call<List<Category>> getCategory();

    @POST("users/login")
    Call<User> loginUser(@Body User user);
}
