package com.example.daval.moviepopularsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetalleActivity extends AppCompatActivity {

    private TextView movieDetail_Title, movieDetail_Description;
    private ImageView movieDetail_Poster;
    public static String IMAGE = "https://image.tmdb.org/t/p/original";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        movieDetail_Title = (TextView) findViewById(R.id.movieDetail_Title);
        movieDetail_Description = (TextView) findViewById(R.id.movieDetail_Description);
        movieDetail_Poster = (ImageView) findViewById(R.id.movieDetail_poster);

        Intent intent = getIntent();
        if(intent.hasExtra("original_Title")){
            String Title = getIntent().getExtras().getString("original_Title", "Mortal Kombat");
            String poster_path = getIntent().getExtras().getString("poster_path");
            String Description = getIntent().getExtras().getString("overview");

            movieDetail_Title.setText(Title);
            Glide.with(this)
                    .load(IMAGE + poster_path)
                    .placeholder(R.drawable.load)
                    .into(movieDetail_Poster);
            movieDetail_Description.setText(Description);
        }else {
            Toast.makeText(this,"NO API DATA" ,Toast.LENGTH_LONG).show();

        }
    }
}