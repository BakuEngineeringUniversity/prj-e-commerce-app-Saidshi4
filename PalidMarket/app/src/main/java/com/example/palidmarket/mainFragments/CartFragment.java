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
import com.example.palidmarket.api.ApiClient;
import com.example.palidmarket.api.ApiInterface;
import com.example.palidmarket.entities.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private CartRecycleViewAdapter cartRecycleViewAdapter;

    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String KEY_ID = "id";

    public CartFragment() {
        // Boş constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.listOfCart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        loadCart();
        return view;
    }

    public void loadCart(){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int userId = sharedPreferences.getInt(KEY_ID, 0);
        Retrofit retrofit = new ApiClient().getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Log.d("nspDebug", "loadCart: " + userId);
        apiInterface.getCart(userId).enqueue(new retrofit2.Callback<List<Cart>>() {
            @Override
            public void onResponse(@NonNull Call<List<Cart>> call, @NonNull Response<List<Cart>> response) {
                List<Cart> carts = response.body();
                cartRecycleViewAdapter = new CartRecycleViewAdapter(getContext(), carts);
                recyclerView.setAdapter(cartRecycleViewAdapter);
                Log.d("nspDebug", "onResponse: " + response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Cart>> call, @NonNull Throwable t) {
                Log.d("nspDebug", "onFailure: " + t.getMessage());
            }
        });

    }
}