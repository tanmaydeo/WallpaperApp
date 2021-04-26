package com.newsify.wallpaperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.newsify.wallpaperapp.databinding.ActivityFullScreenWallpaperBinding;

public class FullScreenWallpaper extends AppCompatActivity {

    PhotoView photoView ;
    ActivityFullScreenWallpaperBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_wallpaper);
        photoView = findViewById(R.id.photoview) ;
        getSupportActionBar().hide();

        String original = getIntent().getStringExtra("originalUrl") ;

        Glide.with(this).load(original).into(photoView) ;
    }
}