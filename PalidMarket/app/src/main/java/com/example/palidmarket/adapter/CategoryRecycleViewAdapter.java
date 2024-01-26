package com.example.palidmarket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.palidmarket.R;
import com.example.palidmarket.adapter.interfaces.RecycleViewInterface;
import com.example.palidmarket.entities.Category;

import java.util.Base64;
import java.util.List;

public class CategoryRecycleViewAdapter extends RecyclerView.Adapter<CategoryRecycleViewAdapter.ViewHolder> {

    private final Context context;
    private final List<Category> categories;
    public final RecycleViewInterface recycleViewInterface;



    public CategoryRecycleViewAdapter(Context context, List<Category> categories, RecycleViewInterface recycleViewInterface) {
        this.context = context;
        this.categories = categories;
        this.recycleViewInterface = recycleViewInterface;
    }


    @NonNull
    @Override
    public CategoryRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecycleViewAdapter.ViewHolder holder, int position) {
        holder.txtItem.setText(categories.get(position).getName() != null ? categories.get(position).getName() : "-");
        if(categories.get(position).getImage() != null) {
            String base64Image = categories.get(position).getImage();
            byte[] decodedString = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                decodedString = Base64.getDecoder().decode(base64Image);
            }
            Glide.with(context)
                    .load(decodedString)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.error_image)
                    .into(holder.imageItem);
        } else {
            holder.imageItem.setImageResource(R.drawable.place_holder);
        }

        holder.itemView.setOnClickListener(v -> {
            int p = holder.getAdapterPosition();
            if(recycleViewInterface != null){
                int itemId = categories.get(p).getId();
                if (p != RecyclerView.NO_POSITION) {
                    recycleViewInterface.onItemClick(itemId);
                    Log.d("nspDebug", "CategoryRecycleViewAdapter.onItemClick: " + itemId);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtItem;
        public ImageView imageItem;


        public ViewHolder(@NonNull View view) {
            super(view);
            txtItem = view.findViewById(R.id.itemCategory);
            imageItem = view.findViewById(R.id.imgCategory);

        }
    }
}