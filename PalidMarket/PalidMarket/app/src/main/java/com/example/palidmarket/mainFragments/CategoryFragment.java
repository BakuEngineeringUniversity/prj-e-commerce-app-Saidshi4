package com.example.palidmarket.mainFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palidmarket.R;
import com.example.palidmarket.adapter.CategoryRecycleViewAdapter;
import com.example.palidmarket.adapter.RecycleViewInterface;
import com.example.palidmarket.api.ApiClient;
import com.example.palidmarket.api.ApiInterface;
import com.example.palidmarket.entities.Category;
import com.example.palidmarket.sideFragments.ProductFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryFragment extends Fragment implements RecycleViewInterface {

    RecyclerView recyclerView;
    int categoryId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView = view.findViewById(R.id.listOfCategories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        loadCategories();
        return view;
    }

    public CategoryFragment() {

    }

    private void loadCategories() {
        Retrofit retrofit = new ApiClient().getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.getCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                List<Category> categories = response.body();

                if (categories != null) {
                    CategoryRecycleViewAdapter adapter = new CategoryRecycleViewAdapter(getActivity(), categories, CategoryFragment.this);
                    recyclerView.setAdapter(adapter);
                    for (Category category : categories) {
                        Log.d("nspDebug", category.getName() != null ? category.getName() : "");
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                Log.d("nspDebug", t.getMessage() != null ? t.getMessage() : "");
            }
        });
    }
    @Override
    public void onItemClick(int categoryId) {
        Log.d("nspDebug", "CategoryFragment.onItemClick: " + categoryId);
        this.categoryId = categoryId;
        loadProductFragment(categoryId);
    }

    private void loadProductFragment(int categoryId) {
        ProductFragment productFragment = new ProductFragment(categoryId);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.productFragmentContainer, productFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}