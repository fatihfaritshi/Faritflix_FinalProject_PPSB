package com.fatih.finalproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fatih.finalproject.Activities.DetailActivity;
import com.fatih.finalproject.Domain.Genres;
import com.fatih.finalproject.Domain.GenresItem;
import com.fatih.finalproject.Domain.ListFilm;
import com.fatih.finalproject.R;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    ArrayList<GenresItem> items;
    Context context;

    public CategoryListAdapter(ArrayList<GenresItem>  items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.ViewHolder holder, int position) {
        holder.TitleTxt.setText(items.get(position).getName());
        holder.itemView.setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TitleTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TitleTxt=itemView.findViewById(R.id.TitleTxt);
        }

    }
}
