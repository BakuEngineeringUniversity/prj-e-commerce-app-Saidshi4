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
import com.example.palidmarket.entities.Category;

import java.util.List;

public class CategoryRecycleViewAdapter extends RecyclerView.Adapter<CategoryRecycleViewAdapter.ViewHolder> {

    private final Context context;
    private final List<Category> categories;

    public CategoryRecycleViewAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }


    @NonNull
    @Override
    public CategoryRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecycleViewAdapter.ViewHolder holder, int position) {
        holder.txtItem.setText(categories.get(position).getName() != null ? categories.get(position).getName() : "-");
        if(categories.get(position).getImage() != null){
            Glide.with(context)
                    .load(categories.get(position).getImage()) // Görsel yolu burada verin
                    .placeholder(R.drawable.place_holder) // Yükleme esnasında gösterilecek yer tutucu
                    .error(R.drawable.error_image) // Hata durumunda gösterilecek yer tutucu
                    .into(holder.imageItem); // ImageView'e yükle
        } else {
            holder.imageItem.setImageResource(R.drawable.place_holder); // Eğer görsel yoksa varsayılan resim
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtItem;
        public ImageView imageItem;

        public ViewHolder(View view) {
            super(view);
            txtItem = view.findViewById(R.id.itemCategory);
            imageItem = view.findViewById(R.id.imgCategory);
        }
    }
}
