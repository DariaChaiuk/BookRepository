package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dal.BookModel;
import com.example.myapplication.dal.DAO_Book;
import com.example.myapplication.gpl.RV_Activity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editName = findViewById(R.id.edit_name);
        final  EditText editAuthor = findViewById(R.id.edit_author);

        Button btnSubmit = findViewById(R.id.btn_submit);
        Button btnOpen = findViewById(R.id.btn_open);
        Button btnBack = findViewById(R.id.back_btn);
        btnBack.setVisibility(View.GONE);
        btnOpen.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RV_Activity.class);
            startActivity(intent);
        });
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        });

        DAO_Book dao = new DAO_Book();
        BookModel empEdit = (BookModel) getIntent().getSerializableExtra("EDIT");
        if (empEdit != null){
            btnBack.setVisibility(View.VISIBLE);
            btnSubmit.setText(R.string.update_text);
            editName.setText(empEdit.getName());
            editAuthor.setText(empEdit.getAuthor());
            btnOpen.setVisibility(View.GONE);
        } else {
            btnSubmit.setText(R.string.submit_text);
            btnOpen.setVisibility(View.VISIBLE);
        }

        btnSubmit.setOnClickListener(view -> {
            BookModel emp =
                    new BookModel(editName.getText().toString(), editAuthor.getText().toString());
            if (empEdit == null) {
                dao.add(emp).addOnSuccessListener(success -> {
                    Toast.makeText(this, "Книга успішно додана", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(error -> {
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                });
            } else {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", editName.getText().toString());
                hashMap.put("author", editAuthor.getText().toString());

                dao.update(empEdit.getKey(), hashMap).addOnSuccessListener(success -> {
                    Toast.makeText(this, "Книга успішно оновлена", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(error -> {
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                });
                btnBack.setVisibility(View.GONE);
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}