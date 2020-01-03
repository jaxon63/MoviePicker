package com.example.el_ja.top100picker;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie_List extends AppCompatActivity implements Serializable {

    private ArrayList<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__list);

        RecyclerView movieListView = findViewById(R.id.movieListView);

        movies = (ArrayList<Movie>)getIntent().getSerializableExtra("movies");

        movieListView.setLayoutManager(new LinearLayoutManager(this));
        movieListView.setAdapter(new MovieAdapater(this, movies));
        movieListView.addItemDecoration(new DividerItemDecoration(movieListView.getContext(), DividerItemDecoration.VERTICAL));


    }
    @Override
    public void onBackPressed(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("movies", movies);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
