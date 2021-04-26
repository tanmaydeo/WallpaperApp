package com.newsify.wallpaperapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    Wallpaperadapter wallpaperadapter ;
    List<WallpaperModel> list = new ArrayList<>() ;
    boolean isScrollimg = false ;
    int pageNo = 1 ;
    int currentItems , scrolloutItems , totalItems ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview) ;
        wallpaperadapter = new Wallpaperadapter(list , this) ;
        recyclerView.setAdapter(wallpaperadapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this , 2) ;
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrollimg = true ;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = gridLayoutManager.getChildCount() ;
                totalItems = gridLayoutManager.getItemCount() ;
                scrolloutItems = gridLayoutManager.findFirstVisibleItemPosition() ;

                if(isScrollimg && (scrolloutItems + currentItems) == totalItems)
                {
                    isScrollimg = false ;
                    fetchWallpaper();
                }
            }
        });

        fetchWallpaper();
    }


    public void fetchWallpaper()
    {
        String url = "https://api.pexels.com/v1/curated/?page="+pageNo+"&per_page=80" ;
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response) ;
                    JSONArray jsonArray = jsonObject.getJSONArray("photos") ;
                    int length = jsonArray.length() ;

                    for(int i = 0 ; i < length ; i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i) ;
                        int id = object.getInt("id") ;

                        JSONObject objectimages = object.getJSONObject("src") ;
                        String originalUrl = objectimages.getString("original") ;
                        String mediumUrl = objectimages.getString("medium") ;

                        list.add(new WallpaperModel(id , originalUrl , mediumUrl)) ;
                    }
                    wallpaperadapter.notifyDataSetChanged();
                    pageNo++ ;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String , String > map = new HashMap<>() ;
                map.put("Authorization" , "563492ad6f9170000100000198ee179956af42f6abd3a4698e2d7c2a") ;
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext()) ;
        requestQueue.add(request) ;
    }
}