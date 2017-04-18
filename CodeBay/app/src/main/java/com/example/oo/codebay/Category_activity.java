package com.example.oo.codebay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Category_activity extends AppCompatActivity {

    private ArrayList<CategoryModel> CategoryList;
    private RecyclerView rvCategory;
    private int item;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_activity);


        CategoryList = new ArrayList<>();


        rvCategory = (RecyclerView) findViewById(R.id.rvCategory);
        GridLayoutManager manager=new GridLayoutManager(this,2);

        rvCategory.setLayoutManager(manager);
        generateCategories();

    }

    public void generateCategories() {
        CategoryList.add(new CategoryModel("Tech", R.drawable.Android));
        CategoryList.add(new CategoryModel("Gadgets", R.drawable.Javascript));
        CategoryList.add(new CategoryModel("Fashion", R.drawable.Python));
        adapter = new CategoryAdapter(CategoryList);
        rvCategory.setAdapter(adapter);


    }

    /*private class RssListAdapter extends RecyclerView.Adapter<CategoryHolder> {
        public RssListAdapter(Context context, ArrayList<CategoryModel> list, int layout) {
            super();
        }
    }*/


}