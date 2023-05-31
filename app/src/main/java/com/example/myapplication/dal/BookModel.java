package com.example.myapplication.dal;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class BookModel implements Serializable {

    @Exclude
    private String key;
    private String name;

    private String author;

    public BookModel(String name, String position) {
        this.name = name;
        this.author = position;
    }

    public BookModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String position) { this.author = position; }
}
