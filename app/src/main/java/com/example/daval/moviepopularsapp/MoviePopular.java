package com.example.daval.moviepopularsapp;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MoviePopular extends Fragment {
    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static String API_KEY = "3e44a4e77556dfabf904db1a138d30dc";
    public static String LANGUAGE = "en-US";
    public static String CATEGORY = "popular";
    public static String IMAGE = "https://image.tmdb.org/t/p/original";

    private List<MovieResults.ResultsBean> ListOfMovies;
    private RecyclerView myRV;
    private RecyclerViewAdapter myAdapter;
    public static final String LOG_TAG = RecyclerViewAdapter.class.getName();


    public MoviePopular() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_popular, container, false);
        myRV = (RecyclerView) view.findViewById(R.id.movieList_recyclerview);
        ListOfMovies = new ArrayList<>();
        myAdapter = new RecyclerViewAdapter(getContext(),ListOfMovies);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            myRV.setLayoutManager(new GridLayoutManager(getContext(),3));
        } else {
            myRV.setLayoutManager(new GridLayoutManager(getContext(),6));
        }
        myRV.setItemAnimator(new DefaultItemAnimator());
        myRV.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        loadData();
        return view;


    }


    private void loadData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface MyInterface = retrofit.create(ApiInterface.class);

        Call<MovieResults> call = MyInterface.getMovies(CATEGORY, API_KEY, LANGUAGE, PAGE);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<MovieResults.ResultsBean> DbMovies = results.getResults();
                myRV.setAdapter(new RecyclerViewAdapter(getContext(),DbMovies));
                myRV.smoothScrollToPosition(0);

            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Error Inesperado!", Toast.LENGTH_LONG).show();
            }
        });

    }
}