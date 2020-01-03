package com.example.el_ja.top100picker;

public class Movie implements java.io.Serializable {
    private String name;
    private Boolean watched;

    public Movie(String name, Boolean watched){
        this.name = name;
        this.watched = watched;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setWatched (Boolean watched){
        this.watched = watched;
    }

    public String getName(){
        return name;
    }

    public Boolean getWatched(){
        return watched;
    }

    public String getWatchedString() {
        if(watched){
            return "true";
        } else {
            return "false";
        }
    }
}
