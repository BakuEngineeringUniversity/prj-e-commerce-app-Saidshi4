package com.example.palidmarket.api;

import com.example.palidmarket.entities.Authentication;
import com.example.palidmarket.entities.Cart;
import com.example.palidmarket.entities.Category;
import com.example.palidmarket.entities.Product;
import com.example.palidmarket.entities.Result;
import com.example.palidmarket.entities.User;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @POST("v1/auth/register")
    Call<Result<User>> saveUser(@Body User user);

    @POST("v1/auth/login")
    Call<Result<Authentication>> loginUser(@Body User user);

    @GET("categories/user/getAll")
    Call<Result<List<Category>>> getCategory(@Header("Authorization") String token);

    @GET("products/user/{getByCategoryId}")
    Call<Result<List<Product>>> getProductsByCategoryId(@Header("Authorization") String token,@Path("getByCategoryId") int getByCategoryId);
    @GET("carts/user/{phoneNumber}")
    Call<Result<List<Cart>>> getCart(@Header("Authorization") String token,@Path("phoneNumber") String phoneNumber);
    @POST("carts/user/{phoneNumber}/{productId}")
    Call<Result> addProductToCart(@Header("Authorization") String token,@Path("phoneNumber") String phoneNumber, @Path("productId") int productId);

    @DELETE("carts/user/{phoneNumber}/{productName}")
    Call<Result> deleteProduct(@Header("Authorization") String token,@Path("phoneNumber") String phoneNumber, @Path("productName") String productName);

    @DELETE("users/delete")
    Call<User> deleteUser(@Body User user);

    @PUT("users/update")
    Call<User> updateUser(@Body User user);

    @PATCH("users/{phoneNumber}/{firstName}")
    Call<User> patchUser(@Path("phoneNumber") String phoneNumber, @Path("firstName") String firstName);

    @PATCH("users/{phoneNumber}/{lastName}")
    Call<User> patchUserLastName(@Path("phoneNumber") String phoneNumber, @Path("lastName") String lastName);
}
