package com.example.palidmarket.api;

import com.example.palidmarket.entities.Category;
import com.example.palidmarket.entities.Product;
import com.example.palidmarket.entities.User;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @POST("users/save")
    Call<User> saveUser(@Body User user);

    @POST("users/login")
    Call<User> loginUser(@Body User user);

    @DELETE("users/delete")
    Call<User> deleteUser(@Body User user);

    @PUT("users/update")
    Call<User> updateUser(@Body User user);

    @PATCH("users/{phoneNumber}/{firstName}")
    Call<User> patchUser(@Path("phoneNumber") String phoneNumber, @Path("firstName") String firstName);

    @PATCH("users/{phoneNumber}/{lastName}")
    Call<User> patchUserLastName(@Path("phoneNumber") String phoneNumber, @Path("lastName") String lastName);

    @GET("categories/getAll")
    Call<List<Category>> getCategory();

    @GET("products/{getByCategoryId}")
    Call<List<Product>> getProductsByCategoryId(int categoryId);
}
