package com.example.myapplication.gpl;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class VH_Book extends RecyclerView.ViewHolder {

    public TextView txtName;
    public TextView txtAuthor;
    public TextView txtOption;


    public VH_Book(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txt_name);
        txtOption = itemView.findViewById(R.id.txt_option);
        txtAuthor = itemView.findViewById(R.id.txt_author);
    }
}
