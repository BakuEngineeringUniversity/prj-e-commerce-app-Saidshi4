package com.example.palidmarket.mainFragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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
import com.example.palidmarket.adapter.interfaces.RecycleViewInterface;
import com.example.palidmarket.api.ApiClient;
import com.example.palidmarket.api.ApiInterface;
import com.example.palidmarket.entities.Category;
import com.example.palidmarket.entities.Result;
import com.example.palidmarket.sideFragments.ProductFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryFragment extends Fragment implements RecycleViewInterface {
    SharedPreferences sharedPreferences;

    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String TOKEN = "token";

    RecyclerView recyclerView;
    int categoryId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN, "");
        Log.d("nspDebug", "Token in onCreateView: " + token);
        Log.d("nspDebug", "Category ID in onCreateView: " + categoryId);

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView = view.findViewById(R.id.listOfCategories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        // Load categories with error handling
        loadCategories(token);
        return view;
    }

    public CategoryFragment() {}

    private void loadCategories(String token) {
        Retrofit retrofit = new ApiClient().getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.getCategory("Bearer " + token).enqueue(new Callback<Result<List<Category>>>() {
            @Override
            public void onResponse(@NonNull Call<Result<List<Category>>> call, @NonNull Response<Result<List<Category>>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<Category> categories = response.body().getData();

                    // Check if categories are not null
                    if (categories != null) {
                        // Create and set adapter for the RecyclerView
                        CategoryRecycleViewAdapter adapter = new CategoryRecycleViewAdapter(getActivity(), categories, CategoryFragment.this);
                        recyclerView.setAdapter(adapter);

                        // Log category names
                        for (Category category : categories) {
                            Log.d("nspDebug", category.getName() != null ? category.getName() : "");
                        }
                    }
                } else {
                    // Handle unsuccessful response
                    handleApiError(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result<List<Category>>> call, @NonNull Throwable t) {
                // Log failure message
                Log.d("nspDebug", t.getMessage() != null ? t.getMessage() : "");
                // Handle network error
                handleNetworkError();
            }
        });
    }

    private void handleApiError(Response<?> response) {
        if (response.code() == 500) {
            // Handle server error
            showToast("Server error");
        } else {
            // Handle other API errors
            showToast("Unexpected error");
        }
    }

    private void handleNetworkError() {
        // Handle network errors
        showToast("Network error");
    }

    private void showToast(String message) {
        // Display a Toast message
        android.widget.Toast.makeText(requireContext(), message, android.widget.Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int categoryId) {
        // Log the selected category ID
        Log.d("nspDebug", "CategoryFragment.onItemClick: " + categoryId);

        // Update the current category ID and load the ProductFragment
        this.categoryId = categoryId;
        loadProductFragment(categoryId);
    }

    private void loadProductFragment(int categoryId) {
        // Begin a FragmentTransaction
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Create a new instance of ProductFragment with the selected category ID
        ProductFragment productFragment = new ProductFragment(categoryId);

        // Replace the existing fragment with the ProductFragment
        transaction.replace(R.id.productFragmentContainer, productFragment);

        // Add the transaction to the back stack
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
