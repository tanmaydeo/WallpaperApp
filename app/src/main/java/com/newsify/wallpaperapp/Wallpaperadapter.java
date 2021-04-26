package com.newsify.wallpaperapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Wallpaperadapter extends RecyclerView.Adapter<Wallpaperadapter.viewholder>{

    private List<WallpaperModel> wallpaperModelList ;
    private Context context ;

    public Wallpaperadapter(List<WallpaperModel> wallpaperModelList, Context context) {
        this.wallpaperModelList =  wallpaperModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item , parent , false) ;
        return new viewholder(view) ;
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull Wallpaperadapter.viewholder holder, int position) {
        Glide.with(context).load(wallpaperModelList.get(position).getMediumUrl()).into(holder.imageView) ;
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , FullScreenWallpaper.class) ;
                intent.putExtra("originalUrl" , wallpaperModelList.get(position).getOriginalUrl()) ;
                context.startActivity(intent);
            }
        });
    }
    @Override
    @Nullable
    public int getItemCount() {
        return wallpaperModelList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView imageView ;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageviewitem) ;
        }
    }
}
