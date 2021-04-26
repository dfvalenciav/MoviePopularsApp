package com.example.daval.moviepopularsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<MovieResults.ResultsBean> mData;
    public static String IMAGE = "https://image.tmdb.org/t/p/original";

    public RecyclerViewAdapter(Context mContext, List<MovieResults.ResultsBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_movielist_Title.setText(mData.get(position).getTitle());
        Glide.with(mContext)
                .load(IMAGE+mData.get(position).getPoster_path())
                .placeholder(R.drawable.load)
                .into(holder.img_movielist_poster);
        holder.cv_movielist_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pos = position;

                MovieResults.ResultsBean clickedDataItem = mData.get(pos);
                Intent intent=new Intent(mContext, DetalleActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("original_Title", mData.get(pos).getOriginal_title());
                intent.putExtra("poster_path",mData.get(pos).getPoster_path());
                intent.putExtra("overview", mData.get(pos).getOverview());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                // Navigation.createNavigateOnClickListener(R.id.action_listMoviesFragment_to_mainActivity2).onClick(holder.itemView);
                Toast.makeText(view.getContext(),"Cargando! " + clickedDataItem.getOriginal_title(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_movielist_Title;
        ImageView img_movielist_poster;
        CardView cv_movielist_cardview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_movielist_Title = (TextView) itemView.findViewById(R.id.movieList_title);
            img_movielist_poster = (ImageView) itemView.findViewById(R.id.movieList_poster);
            cv_movielist_cardview = (CardView) itemView.findViewById(R.id.movieList_cardview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
