package com.example.el_ja.top100picker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView randomMovie = findViewById(R.id.randomMovie);
        Button pickButton = findViewById(R.id.pickButton);
        final Switch watchedMovie = findViewById(R.id.watchedMovies);
        final Button setWatched = findViewById(R.id.setWatched);

        try {
            JSONObject jsonObj = new JSONObject(loadJSONString());
            JSONArray json = jsonObj.getJSONArray("movies");
            movies = new ArrayList<>();
            for(int i = 0; i < json.length(); i++){
                JSONObject obj = json.getJSONObject(i);
                movies.add(new Movie(obj.getString("name"), obj.getBoolean("watched")));
            }
        } catch (Exception ex){
            ex.printStackTrace();
            randomMovie.setText("Error picking movie");
        }
        final Random generator = new Random();



        pickButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                for(int i = 0; i < movies.size(); i++){
                    final int index = generator.nextInt(movies.size());
                    if(!watchedMovie.isChecked()){
                        if(movies.get(index).getWatched()){
                            continue;
                        }
                    }
                    randomMovie.setText(movies.get(index).getName());
                    setWatched.setVisibility(View.VISIBLE);
                    setWatched.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            setToWatched(index);
                        }
                    });
                }

            }
        });
    }

    private void setToWatched(int index){
        try {
            JSONObject jsonObj = new JSONObject(loadJSONString());
            JSONArray json = jsonObj.getJSONArray("movies");
            JSONObject obj = json.getJSONObject(index).put("watched", true);


        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private String loadJSONString(){
        String json = null;
        try{
            InputStream is = getResources().openRawResource(R.raw.movies);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
