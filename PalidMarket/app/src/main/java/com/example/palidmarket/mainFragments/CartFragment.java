package com.example.palidmarket.mainFragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.palidmarket.R;
import com.example.palidmarket.adapter.CartRecycleViewAdapter;
import com.example.palidmarket.adapter.interfaces.CartRecycleViewInterface;
import com.example.palidmarket.api.ApiClient;
import com.example.palidmarket.api.ApiInterface;
import com.example.palidmarket.entities.Cart;
import com.example.palidmarket.entities.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartFragment extends Fragment implements CartRecycleViewInterface {
    private RecyclerView recyclerView;
    private CartRecycleViewAdapter cartRecycleViewAdapter;

    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String PHONE_NUMBER = "phoneNumber";
    SharedPreferences sharedPreferences;
    private static final String TOKEN = "token";


    public CartFragment() {
        // Boş constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN, "");
        String phoneNumber = sharedPreferences.getString(PHONE_NUMBER,"");
        Log.d("nspDebug", "token in onCreate: " + token);
        Log.d("nspDebug", "phoneNumber in onCreate: " + phoneNumber);
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.listOfCart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        loadCart(token,phoneNumber);
        return view;
    }

    public void loadCart(String token, String phoneNumber){

        Retrofit retrofit = new ApiClient().getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Log.d("nspDebug", "loadCart: " + phoneNumber);
        apiInterface.getCart("Bearer " + token, phoneNumber).enqueue(new retrofit2.Callback<Result<List<Cart>>>() {
            @Override
            public void onResponse(@NonNull Call<Result<List<Cart>>> call, @NonNull Response<Result<List<Cart>>> response) {
                if (response.body() != null) {
                    Result<List<Cart>> result = response.body();
                    List<Cart> carts = result.getData();
                    cartRecycleViewAdapter = new CartRecycleViewAdapter(getContext(), carts, CartFragment.this);
                    recyclerView.setAdapter(cartRecycleViewAdapter);
                    Log.d("nspDebug", "onResponse: " + response.body());
                }else {
                    Log.e("nspDebug","response body is null");
                }

            }

            @Override
            public void onFailure(@NonNull Call<Result<List<Cart>>> call, @NonNull Throwable t) {
                Log.d("nspDebug", "onFailure: " + t.getMessage());
            }
        });

    }

    @Override
    public void deleteProduct(String productName) {
        Log.d("nspDebug", "deleteProduct: " + productName);
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString(PHONE_NUMBER, "");
        String token = sharedPreferences.getString(TOKEN, "");

        Retrofit retrofit = new ApiClient().getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.deleteProduct("Bearer " + token, phoneNumber,productName).enqueue(new retrofit2.Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                if (response.body() != null) {
                    Result result = response.body();
                    Log.d("nspDebug", "onResponse: " + result.getMessage());
                    loadCart(token,phoneNumber);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {

            }
        });
    }
}