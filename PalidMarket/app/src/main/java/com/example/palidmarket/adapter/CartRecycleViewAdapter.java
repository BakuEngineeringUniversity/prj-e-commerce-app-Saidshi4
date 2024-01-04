package com.example.palidmarket.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palidmarket.R;
import com.example.palidmarket.entities.Cart;
import com.example.palidmarket.entities.Product;

import java.util.List;

public class CartRecycleViewAdapter extends RecyclerView.Adapter<CartRecycleViewAdapter.ViewHolder> {

    private final Context context;
    private final List<Cart> carts;

    public CartRecycleViewAdapter(Context context, List<Cart> carts) {
        this.context = context;
        this.carts = carts;
    }

    @NonNull
    @Override
    public CartRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartRecycleViewAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecycleViewAdapter.ViewHolder holder, int position) {
        Product product = carts.get(position).getProduct();
        holder.txtNameCart.setText(product.getName());
        holder.txtPriceCart.setText(String.valueOf(product.getUnitPrice()));
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNameCart;
        public TextView txtPriceCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameCart = itemView.findViewById(R.id.txtProductNameCart);
            txtPriceCart = itemView.findViewById(R.id.txtProductPriceCart);
        }
    }
}
