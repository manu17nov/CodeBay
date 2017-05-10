package com.example.oo.codebay;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.R.attr.data;
import static android.R.attr.name;
import static com.example.oo.codebay.LibraryAdapter.LIBRARY_KEY;
import static com.example.oo.codebay.LibraryAdapter.PARENT_KEY;

public class DetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String library_key = getIntent().getStringExtra(LIBRARY_KEY);
        final String type_key = getIntent().getStringExtra(PARENT_KEY);
        final TextView creator_id = (TextView) findViewById(R.id.creator_id);
        final TextView desc_id = (TextView) findViewById(R.id.desc_id);
        final TextView name_id = (TextView) findViewById(R.id.name_id);
        final TextView url_id = (TextView) findViewById(R.id.url_id);
        final ImageView img_id = (ImageView) findViewById(R.id.img_id);
        final RatingBar ratingbar_id = (RatingBar) findViewById(R.id.ratingbar_id);
        DatabaseReference libRef = FirebaseDatabase.getInstance().getReference("Libraries").child(type_key).child(library_key);
        if (libRef != null) {
            libRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.hasChildren()) {
                        String name = snapshot.child("name").getValue(String.class);
                        if (name != null)
                            name_id.setText(name);
                        String desc = snapshot.child("desc").getValue(String.class);
                        if (desc != null)
                            desc_id.setText(desc);

                        String creator = snapshot.child("creator").getValue(String.class);
                        if (creator != null)
                            creator_id.setText(creator);
                        String url = snapshot.child("url").getValue(String.class);
                        if (url != null)
                            url_id.setText(url);
                        String imageURl = snapshot.child("img").getValue(String.class);
                        Picasso.with(DetailsActivity.this).load(imageURl).error(R.drawable.library).placeholder(R.drawable.logo).into(img_id);
                        ratingbar_id.setRating(snapshot.child("rating").getValue(Integer.class));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    if (databaseError != null) {
                        Toast.makeText(DetailsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        ratingbar_id.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {
                if (fromUser) {
                    Toast.makeText(DetailsActivity.this, "updating...", Toast.LENGTH_SHORT).show();
                    final DatabaseReference countRef = FirebaseDatabase.getInstance().getReference("Libraries").child(type_key).child(library_key).child("rating_count");
                    final DatabaseReference ratingRef = FirebaseDatabase.getInstance().getReference("Libraries").child(type_key).child(library_key).child("rating");
                    countRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int counter = 0;
                            try {
                                counter = dataSnapshot.getValue(Integer.class);
                            } catch (Exception e) {
                                counter = 0;
                            }
                            counter++;
                            countRef.setValue(counter);
                            final int finalCounter = counter;
                            ratingRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int oldrating = dataSnapshot.getValue(Integer.class);
                                    int newRating = Math.round((rating+oldrating) / 2);
                                    ratingRef.setValue(newRating);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}