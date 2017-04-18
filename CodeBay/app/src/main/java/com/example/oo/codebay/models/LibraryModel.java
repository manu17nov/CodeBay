package com.example.oo.codebay.models;

/**
 * Created by o.O on 4/13/2017.
 */

public class LibraryModel {
    String creator;
    String desc;
    String img;
    String name;
    int rating;
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

    public LibraryModel(String creator, String desc, String img, String name, int rating, String url) {
        this.creator = creator;
        this.desc = desc;
        this.img = img;
        this.name = name;
        this.rating = rating;
        this.url = url;

    }
}
