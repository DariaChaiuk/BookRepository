package com.example.myapplication.bll;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.dal.BookModel;
import com.example.myapplication.dal.DAO_Book;
import com.example.myapplication.gpl.VH_Book;

import java.util.ArrayList;

public class RV_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    ArrayList<BookModel> list = new ArrayList<>();

    public RV_Adapter(Context ctx){
        this.context = ctx;
    }

    public void setItems(ArrayList<BookModel> bookList){
        list.addAll(bookList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        return new VH_Book(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        this.onBindViewHolder(holder, position, (BookModel) null);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, BookModel bookModel) {
        VH_Book vh = (VH_Book) holder;
        BookModel book = (bookModel == null) ? list.get(position) : bookModel;

        vh.txtName.setText((book.getName()));
        vh.txtAuthor.setText(book.getAuthor());

        vh.txtOption.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, vh.txtOption);
            popupMenu.inflate(R.menu.option_menu);

            popupMenu.setOnMenuItemClickListener(item -> {
                int menuItemId = item.getItemId();
                if(menuItemId == R.id.menu_edit) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("EDIT", book);
                    context.startActivity(intent);
                } else if (menuItemId == R.id.menu_remove) {
                    DAO_Book dao = new DAO_Book();
                    dao.remove(book.getKey()).addOnSuccessListener(success -> {
                        Toast.makeText(context, "Книга успішно видалена",
                                Toast.LENGTH_LONG).show();
                        notifyItemRemoved(position);
                        list.remove(book);
                    }).addOnFailureListener(error -> {
                        Toast.makeText(context, "Помилка видалення: " + error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    });
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
