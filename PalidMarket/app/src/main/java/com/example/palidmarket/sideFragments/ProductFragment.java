package com.example.palidmarket.sideFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palidmarket.R;
import com.example.palidmarket.adapter.CategoryRecycleViewAdapter;
import com.example.palidmarket.adapter.ProductRecycleViewAdapter;
import com.example.palidmarket.api.ApiClient;
import com.example.palidmarket.api.ApiInterface;
import com.example.palidmarket.entities.Category;
import com.example.palidmarket.entities.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductRecycleViewAdapter productAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = view.findViewById(R.id.listOfProducts); // Burada RecyclerView'ı layout dosyasından doğru şekilde alıyorum.

        // RecyclerView'ı yapılandırma
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        productAdapter = new ProductRecycleViewAdapter(getActivity(), new ArrayList<>()); // Boş bir listeyle adapter'ı oluşturuyoruz
        recyclerView.setAdapter(productAdapter);

        loadProducts(); // API'den ürünleri almak için bu metodu çağırıyorum.
        return view;
    }

    private void loadProducts() {
        Retrofit retrofit = new ApiClient().getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.getProductsByCategoryId(5).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                List<Product> products = response.body();

                if (products != null) {
                    // Gelen ürünleri adapter'a set ediyoruz
                    productAdapter.setData(products);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.d("nspDebug", t.getMessage() != null ? t.getMessage() : "");
            }
        });
    }
}
