package com.mobile.openlibraryapp;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import book.Book1;
import category.Category;
import category.CategoryAdapter;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recvCategory;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recvCategory = findViewById(R.id.recv_category);
        categoryAdapter = new CategoryAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recvCategory.setLayoutManager(linearLayoutManager);

        categoryAdapter.setData(getListCategory());
        recvCategory.setAdapter(categoryAdapter);
    }

    private List<Category> getListCategory() {
        List<Category>listCategory = new ArrayList<>();

        List<Book1> listBook = new ArrayList<>();
        listBook.add(new Book1(R.drawable.book,"Read"));
        listBook.add(new Book1(R.drawable.windwillow,"Locate"));
        listBook.add(new Book1(R.drawable.snowhite,"Locate"));
        listBook.add(new Book1(R.drawable.harry,"Read"));
        listBook.add(new Book1(R.drawable.elsa,"Read"));
        listBook.add(new Book1(R.drawable.beautybeaste,"Locate"));
        listBook.add(new Book1(R.drawable.traindragon,"Read"));

        listBook.add(new Book1(R.drawable.harry,"Read"));
        listBook.add(new Book1(R.drawable.elsa,"Locate"));
        listBook.add(new Book1(R.drawable.beautybeaste,"Read"));
        listBook.add(new Book1(R.drawable.traindragon,"Locate"));

        listCategory.add(new Category("Trending Books",listBook));
        listCategory.add(new Category("Classic Books",listBook));
        listCategory.add(new Category("History Books",listBook));
        listCategory.add(new Category("Magic Books",listBook));
        return listCategory;
    }
}