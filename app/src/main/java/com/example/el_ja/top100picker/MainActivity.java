package com.example.el_ja.top100picker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements java.io.Serializable {

    private ArrayList<Movie> movies;
    private String masterJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadJsonInternal();

        final TextView randomMovie = findViewById(R.id.randomMovie);
        Button pickButton = findViewById(R.id.pickButton);
        final Switch watchedMovie = findViewById(R.id.watchedMovies);
        final Button setWatched = findViewById(R.id.setWatched);
        Button listButton = findViewById(R.id.ListButton);


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
                        movies.get(index).setWatched(true);
                        saveJson();
                        Toast.makeText(getBaseContext(), "Movie added to watched list", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Movie_List.class);
                intent.putExtra("movies", movies);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                movies = (ArrayList<Movie>)data.getSerializableExtra("movies");
                saveJson();
            }
        }
    }


    private void saveJson(){
        JSONObject moviesJson = new JSONObject();
        JSONArray movieListJson = new JSONArray();
        try {
            for (Movie mov : movies) {
                JSONObject movieJson = new JSONObject();
                movieJson.put("name", mov.getName());
                movieJson.put("watched", mov.getWatched());

                movieListJson.put(movieJson);
            }

            moviesJson.put("movies", movieListJson);
        } catch (JSONException jsonEx){
            jsonEx.printStackTrace();
        }

        masterJson = moviesJson.toString();
        writeInternal();


    }

    private void loadJSONStringFromRaw(){
        masterJson = null;
        try{
            InputStream is = getResources().openRawResource(R.raw.movies);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            masterJson = new String(buffer, "UTF-8");

            writeInternal();

            jsonToObject();

        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }

    private void writeInternal(){
        File internalFile = new File(getFilesDir(), "movies.json");

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(internalFile));
            bw.write(masterJson);
            bw.flush();
            bw.close();
            Log.d("writeInternal", "File successfully written internally");
        } catch (IOException ioEx){
            ioEx.printStackTrace();
        }
    }

    private void loadJsonInternal(){
        masterJson = null;
        File internalFile = new File(getFilesDir(), "movies.json");

        try{
            FileInputStream inputStream = new FileInputStream(internalFile);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            masterJson = new String(buffer, "UTF-8");
            jsonToObject();

        } catch (IOException ioEx){
            ioEx.printStackTrace();
            loadJSONStringFromRaw();
        }
    }

    private void jsonToObject(){
        try {
            JSONObject jsonObj = new JSONObject(masterJson);
            JSONArray json = jsonObj.getJSONArray("movies");
            movies = new ArrayList<>();
            for(int i = 0; i < json.length(); i++){
                JSONObject obj = json.getJSONObject(i);
                movies.add(new Movie(obj.getString("name"), obj.getBoolean("watched")));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
