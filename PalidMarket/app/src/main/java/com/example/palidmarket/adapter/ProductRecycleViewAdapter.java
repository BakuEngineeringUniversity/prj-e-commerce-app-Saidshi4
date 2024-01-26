package com.example.palidmarket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palidmarket.R;
import com.example.palidmarket.adapter.interfaces.ProductRecycleViewInterFace;
import com.example.palidmarket.entities.Product;

import java.util.List;

public class ProductRecycleViewAdapter extends RecyclerView.Adapter<ProductRecycleViewAdapter.ViewHolder> {
    private final Context context;
    private final List<Product> products;
    public final ProductRecycleViewInterFace productRecycleViewInterFace;

    public ProductRecycleViewAdapter(Context context, List<Product> products, ProductRecycleViewInterFace productRecycleViewInterFace) {
        this.context = context;
        this.products = products;
        this.productRecycleViewInterFace = productRecycleViewInterFace;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(products.get(position).getName());
        holder.txtPrice.setText(products.get(position).getUnitPrice());

        holder.btnAddToCart.setOnClickListener(v -> {
            int p = holder.getAdapterPosition();
            if (productRecycleViewInterFace != null) {
                int itemId = products.get(p).getId();
                if (p != RecyclerView.NO_POSITION) {
                    productRecycleViewInterFace.addProductToCart(itemId);
                    Log.d("nspDebug", "CategoryRecycleViewAdapter.onItemClick: " + itemId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtPrice;
        public Button btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtProductPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
