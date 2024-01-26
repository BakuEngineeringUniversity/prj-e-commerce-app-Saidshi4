package com.example.palidmarket.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palidmarket.R;
import com.example.palidmarket.adapter.interfaces.CartRecycleViewInterface;
import com.example.palidmarket.entities.Cart;
import com.example.palidmarket.entities.Product;

import java.util.List;

public class CartRecycleViewAdapter extends RecyclerView.Adapter<CartRecycleViewAdapter.ViewHolder> {

    private final Context context;
    private final List<Cart> carts;
    public final CartRecycleViewInterface cartRecycleViewInterface;

    public CartRecycleViewAdapter(Context context, List<Cart> carts, CartRecycleViewInterface cartRecycleViewInterface) {
        this.context = context;
        this.carts = carts;
        this.cartRecycleViewInterface = cartRecycleViewInterface;
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

        holder.btnDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p = holder.getAdapterPosition();
                if (cartRecycleViewInterface != null) {
                    String  itemId = carts.get(p).getProduct().getName();
                    if (p != RecyclerView.NO_POSITION) {
                        cartRecycleViewInterface.deleteProduct(itemId);
                        Log.d("nspDebug", "CartRecycleViewAdapter.onItemClick: " + itemId);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNameCart;
        public TextView txtPriceCart;
        public Button btnDeleteCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameCart = itemView.findViewById(R.id.txtProductNameCart);
            txtPriceCart = itemView.findViewById(R.id.txtProductPriceCart);
            btnDeleteCart = itemView.findViewById(R.id.deleteProduct);
        }
    }
}
