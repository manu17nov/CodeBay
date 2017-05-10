package com.example.oo.codebay.models;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by o.O on 4/13/2017.
 */

public class LibraryModel {
    private final String type;
    private final String key;
    String creator;
    String desc;
    String img;
    String name;
    int rating;
    int count;
    String url;

    public String getCreator() {
        return creator;
    }

    public String getDesc() {
        return desc;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public LibraryModel(DataSnapshot snapshot, String type) {
        this.creator = snapshot.child("creator").getValue(String.class);
        this.desc = snapshot.child("desc").getValue(String.class);
        this.img = snapshot.child("img").getValue(String.class);
        this.name = snapshot.child("name").getValue(String.class);
        this.rating = snapshot.child("rating").getValue(Integer.class);
        try {
            this.count = snapshot.child("rating_count").getValue(Integer.class);
        } catch (Exception e) {
            count=0;
        }
        this.url = snapshot.child("url").getValue(String.class);
        ;
        this.type = type;
        key = snapshot.getKey();


    }

    public String getKey() {
        return key;
    }
}
