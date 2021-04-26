package com.example.daval.moviepopularsapp;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class DetalleActivity extends AppCompatActivity {

    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static String API_KEY = "3e44a4e77556dfabf904db1a138d30dc";
    public static String LANGUAGE = "en-US";
    public static String CATEGORY = "popular";
    public static String IMAGE = "https://image.tmdb.org/t/p/original";

    private TextView movieDetail_Title, movieDetail_Description;
    private ImageView movieDetail_Poster;


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
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiInterface MyInterface = retrofit.create(ApiInterface.class);

            Call<MovieResults> call = MyInterface.getMovies(CATEGORY,API_KEY,LANGUAGE,PAGE);

            call.enqueue(new Callback<MovieResults>() {
                @Override
                public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                    MovieResults results = response.body();
                    List<MovieResults.ResultsBean> listOfMovies =  results.getResults();
                    MovieResults.ResultsBean firstmovie = listOfMovies.get(0);
                    movieDetail_Title.setText(firstmovie.getTitle());
                    String ImagePath = firstmovie.getPoster_path();
                    Glide.with(DetalleActivity.this)
                            .load(IMAGE+ImagePath)
                            .into(movieDetail_Poster);
                    movieDetail_Description.setText(firstmovie.getOverview());

                }

                @Override
                public void onFailure(Call<MovieResults> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        }
    }
}