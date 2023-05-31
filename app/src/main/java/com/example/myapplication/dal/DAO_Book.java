package com.example.myapplication.dal;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAO_Book {

    private final DatabaseReference dbReference;
    private final int paginationSize;

    public DAO_Book() {
        String dbURL = "https://bookapp-103b2-default-rtdb.firebaseio.com/";
        FirebaseDatabase dbConnection = FirebaseDatabase.getInstance(dbURL);
        dbReference = dbConnection.getReference(BookModel.class.getSimpleName());
        paginationSize = 5;
    }

    public Task<Void> add(BookModel book) {
        return dbReference.push().setValue(book);
    }

    public  Task<Void> update(String key, HashMap<String, Object> hashMap){
        return dbReference.child(key).updateChildren(hashMap);
    }

    public  Task<Void> remove(String key){
        return dbReference.child(key).removeValue();
    }

    public Query get(String key){
        if(key == null){
            return dbReference.orderByKey().limitToFirst(paginationSize);
        } else {
            return dbReference.orderByKey().startAfter(key).limitToFirst(paginationSize);
        }
    }

    public Query get(){
        return dbReference;
    }
}
