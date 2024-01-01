package com.example.palidmarket.sideFragments;

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


import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductFragment extends Fragment {

    private int categoryId;
    private RecyclerView recyclerView;
    private ProductRecycleViewAdapter productAdapter;

    public ProductFragment(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = view.findViewById(R.id.listOfProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        loadProducts(categoryId);
        return view;
    }
    public ProductFragment() {

    }

    private void loadProducts(int categoryId) {
        Retrofit retrofit = new ApiClient().getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.getProductsByCategoryId(categoryId).enqueue(new retrofit2.Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                List<Product> products = response.body();
                if (products != null) {
                    productAdapter = new ProductRecycleViewAdapter(requireContext(), products);
                    recyclerView.setAdapter(productAdapter);
                    for (Product product : products) {
                        Log.d("nspDebug", product.getName() != null ? product.getName() : "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.e("Error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
