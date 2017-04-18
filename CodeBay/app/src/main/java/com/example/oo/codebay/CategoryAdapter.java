package com.example.oo.codebay;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.example.oo.codebay.CategoryHolder;
import com.example.oo.codebay.CategoryModel;
import com.example.oo.codebay.models.LibraryModel;
import com.example.oo.codebay.R;

/**
 * Created by hp on 05-Apr-17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

    private List<CategoryModel> CategoryList;

    public CategoryAdapter(List<CategoryModel> categoryList) {
        CategoryList = categoryList;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row=((LayoutInflater)parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.simple_category_item,parent,false);
        return new CategoryHolder(row);
    }

    @Override
    public void onBindViewHolder(final CategoryHolder holder, final int position) {
        final CategoryModel categoryModel = CategoryList.get(position);
        holder.tvLabel.setText(categoryModel.label);
        holder.rlContainer.setTag(categoryModel);
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryModel tag = (CategoryModel) holder.rlContainer.getTag();
                Intent i = new Intent(v.getContext(),ChoiceActivity.class);
                i.putExtra("com.example.oo.codebay.EXTRA_CATEGORY",tag.label);
                i.putExtra("com.example.oo.codebay.EXTRA_POS",position);
                v.getContext().startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return CategoryList.size();

    }


}
