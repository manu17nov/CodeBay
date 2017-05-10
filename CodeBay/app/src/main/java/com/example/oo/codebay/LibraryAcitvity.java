package com.example.oo.codebay;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.oo.codebay.models.LibraryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LibraryAcitvity extends AppCompatActivity {

    private ArrayList<LibraryModel> categoryList;
    private RecyclerView rvCategory;
    private LibraryAdapter adapter;
    private DatabaseReference libRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        libRef = FirebaseDatabase.getInstance().getReference("Libraries");

        categoryList = new ArrayList<>();

        rvCategory = (RecyclerView) findViewById(R.id.rvCategory);
        GridLayoutManager manager = new GridLayoutManager(this, 2);

        rvCategory.setLayoutManager(manager);
        generateCategories();

    }

    public void generateCategories() {
        libRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    if (dataSnapshot.child("Android").hasChildren()) {
                        for (DataSnapshot snapshot : dataSnapshot.child("Android").getChildren()) {
                            categoryList.add(new LibraryModel(snapshot,"Android"));
                        }

                    }
                    if (dataSnapshot.child("Javascript").hasChildren()) {
                        for (DataSnapshot snapshot : dataSnapshot.child("Javascript").getChildren()) {
                            categoryList.add(new LibraryModel(snapshot, "Javascript"));
                        }

                    }
                    if (dataSnapshot.child("Python").hasChildren()) {
                        for (DataSnapshot snapshot : dataSnapshot.child("Python").getChildren()) {
                            categoryList.add(new LibraryModel(snapshot, "Python"));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (databaseError != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LibraryAcitvity.this)
                            .setMessage("Could Not fetch Details " + databaseError.getMessage())
                            .setIcon(R.drawable.library)
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(LibraryAcitvity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.create().show();
                }
            }
        });
        adapter = new LibraryAdapter(categoryList);
        rvCategory.setAdapter(adapter);
    }
}