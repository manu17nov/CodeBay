package com.example.oo.codebay;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oo.codebay.models.LibraryModel;

import java.util.List;

/**
 * Created by hp on 05-Apr-17.
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryHolder> {

    public static final String LIBRARY_KEY = "com.example.oo.codebay.libraryKey";
    public static final String PARENT_KEY = "com.example.oo.codebay.parent";
    private List<LibraryModel> list;
    int[] logos= new int[]{
            R.drawable.android_logo,
            R.drawable.python_logo,
            R.drawable.js_logo,
    };
    public LibraryAdapter(List<LibraryModel> list) {
        this.list = list;
    }

    @Override
    public LibraryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = ((LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.simple_library_item, parent, false);
        return new LibraryHolder(row);
    }

    @Override
    public void onBindViewHolder(final LibraryHolder holder, final int position) {
        final LibraryModel model = list.get(position);
        holder.tvName.setText(model.getName());
        holder.tvCreator.setText(model.getCreator());
        holder.tvCategory.setText(model.getType());
        holder.rbRating.setRating(model.getRating());
        if (model.getType()=="Android") {
            holder.ivLogo.setImageResource(logos[0]);
        }
        if (model.getType()=="Javascript") {
            holder.ivLogo.setImageResource(logos[1]);
        }
        if (model.getType()=="Python") {
            holder.ivLogo.setImageResource(logos[2]);
        }

        holder.rlContainer.setTag(model);
        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibraryModel tag = (LibraryModel) holder.rlContainer.getTag();
                Intent i = new Intent(v.getContext(), DetailsActivity.class);
                i.putExtra(LIBRARY_KEY,model.getKey());
                i.putExtra(PARENT_KEY,model.getType());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public void refreshLibraryList(List<LibraryModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
