package com.example.el_ja.top100picker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MovieAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Movie> movies;
    private Context context;

    public MovieAdapater(Context context, ArrayList<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.movie_row, viewGroup, false);
        Item item = new Item(row);

        return item;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {
        ((Item)viewHolder).movieText.setText(movies.get(i).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movies.get(i).getWatched()){
                    movies.get(i).setWatched(false);
                } else {
                    movies.get(i).setWatched(true);
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("movies", movies);
                notifyDataSetChanged();


            }
        });
        if(movies.get(i).getWatched()){
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#4bd624"));
        } else {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#fc6703"));
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class Item extends RecyclerView.ViewHolder {

        TextView movieText;

        public Item(View itemView) {
            super(itemView);
            movieText = itemView.findViewById(R.id.movieItem);
        }
    }

}
