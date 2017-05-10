package com.example.oo.codebay;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class LibraryHolder extends RecyclerView.ViewHolder {
    TextView tvName;
    TextView tvCreator;
    TextView tvCategory;
    View rlContainer;
    RatingBar rbRating;
    ImageView ivLogo;
        Button btnDetails;
    public LibraryHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
        tvCreator = (TextView) itemView.findViewById(R.id.tvCreator);
        tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
        rlContainer = itemView.findViewById(R.id.rlcontainer);
        ivLogo= (ImageView) itemView.findViewById(R.id.ivLogo);
        rbRating= (RatingBar) itemView.findViewById(R.id.rbRating);
        btnDetails= (Button) itemView.findViewById(R.id.btnDetails);

    }
}
