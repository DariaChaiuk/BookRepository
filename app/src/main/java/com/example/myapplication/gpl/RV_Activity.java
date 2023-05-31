package com.example.myapplication.gpl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.bll.RV_Adapter;
import com.example.myapplication.dal.BookModel;
import com.example.myapplication.dal.DAO_Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RV_Activity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    Button homeBack;
    RV_Adapter adapter;
    DAO_Book dao;
    boolean isLoading = false;
    String key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        swipeRefreshLayout = findViewById(R.id.swiper);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        homeBack = findViewById(R.id.back_btn);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new RV_Adapter(this);
        recyclerView.setAdapter(adapter);
        dao = new DAO_Book();

        loadData();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy){
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(manager == null) throw new AssertionError();
                int totalItem = manager.getItemCount();
                int lastVisible = manager.findLastCompletelyVisibleItemPosition();

                if (totalItem < lastVisible + 3) {
                    if(!isLoading) {
                        isLoading = true;
                        loadData();
                    }
                }
            }
        });

        homeBack.setOnClickListener(view -> {
            Intent intent = new Intent(RV_Activity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        dao.get(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<BookModel> employees = new ArrayList<>();
                for (DataSnapshot data: snapshot.getChildren()) {
                    BookModel book = data.getValue(BookModel.class);
                    if(book == null) throw  new AssertionError();
                    key = data.getKey();
                    book.setKey(key);
                    employees.add(book);
                }

                adapter.setItems(employees);
                adapter.notifyDataSetChanged();
                isLoading = false;
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}