package com.example.palidmarket.sideFragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import com.example.palidmarket.R;
import com.example.palidmarket.adapter.ProductRecycleViewAdapter;
import com.example.palidmarket.api.ApiClient;
import com.example.palidmarket.api.ApiInterface;
import com.example.palidmarket.entities.Product;
import com.example.palidmarket.entities.Result;


import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductFragment extends Fragment {

    private int categoryId;
    private RecyclerView recyclerView;
    private ProductRecycleViewAdapter productAdapter;
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String TOKEN = "token";

    public ProductFragment(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN, "");
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = view.findViewById(R.id.listOfProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        loadProducts(token,categoryId);
        return view;
    }
    public ProductFragment() {

    }

    private void loadProducts(String token, int categoryId) {
        Retrofit retrofit = new ApiClient().getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.getProductsByCategoryId("Bearer " + token, categoryId).enqueue(new retrofit2.Callback<Result<List<Product>>>() {
            @Override
            public void onResponse(@NonNull Call<Result<List<Product>>> call, @NonNull Response<Result<List<Product>>> response) {
                if (response.body() != null) {
                    Result<List<Product>> result = response.body();
                    List<Product> products = result.getData();

                    if (products != null) {
                        productAdapter = new ProductRecycleViewAdapter(requireContext(), products);
                        recyclerView.setAdapter(productAdapter);

                        for (Product product : products) {
                            Log.d("nspDebug", product.getName() != null ? product.getName() : "");
                        }
                    }
                } else {
                    Log.e("nspDebug","response body is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result<List<Product>>> call, @NonNull Throwable t) {
                Log.e("Error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
