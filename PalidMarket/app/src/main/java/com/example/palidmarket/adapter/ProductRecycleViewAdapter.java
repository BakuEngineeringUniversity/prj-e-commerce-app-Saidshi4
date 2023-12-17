package com.example.palidmarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.palidmarket.R;
import com.example.palidmarket.entities.Product;

import java.util.Base64;
import java.util.List;

public class ProductRecycleViewAdapter extends RecyclerView.Adapter<ProductRecycleViewAdapter.ViewHolder> {
    private final Context context;
    private List<Product> products;

    public void setData(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(int categoryId);
    }


    public ProductRecycleViewAdapter(Context context, List<Product> products ) {
        this.context = context;
        this.products = products;
    }


    @NonNull
    @Override
    public ProductRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecycleViewAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(products.get(position).getName());
        holder.txtPrice.setText(products.get(position).getUnitPrice());


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtPrice;
        public ImageView imageProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.productName);
            txtPrice = itemView.findViewById(R.id.unitPrice);
            imageProduct = itemView.findViewById(R.id.imageProduct);
        }
    }
}
