package com.example.oo.codebay;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oo.codebay.models.LibraryModel;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static com.example.oo.codebay.R.id.tvName;
import static com.example.oo.codebay.RegisterCodeBay.mGoogleApiClient;
import static com.facebook.internal.CallbackManagerImpl.RequestCodeOffset.AppInvite;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<LibraryModel> libraryList;
    private RecyclerView rvCategory;
    private static final int REQUEST_INVITE = 0;
    private LibraryAdapter adapter;
    public static final String TAG = "HomeActivity";
    private DatabaseReference libRef;
    private SearchView svSearch;
    private ProgressBar pbStatus;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private int pos;
    private ArrayList<LibraryModel> searchList;
    private FirebaseAuth auth;

    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        svSearch = (SearchView) findViewById(R.id.svSearch);
        pbStatus = (ProgressBar) findViewById(R.id.pbStatus);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        libRef = FirebaseDatabase.getInstance().getReference("Libraries");
        auth = FirebaseAuth.getInstance();

        libraryList = new ArrayList<>();
        searchList = new ArrayList<>();
        rvCategory = (RecyclerView) findViewById(R.id.rvCategory);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        rvCategory.setLayoutManager(manager);
        adapter = new LibraryAdapter(libraryList);
        rvCategory.setAdapter(adapter);

        generateCategories();


        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "please enter text", Toast.LENGTH_SHORT).show();
                } else {
                    searchList.clear();

                    Iterator<LibraryModel> iterator = libraryList.iterator();
                    while (iterator.hasNext()) {
                        LibraryModel model = iterator.next();
                        if (model.getName() != null && model.getName().contains(query)) {
                            searchList.add(model);
                        }

                    }
                    if (searchList.size() > 0) {
                        adapter.refreshLibraryList(searchList);
                    } else {
                        Toast.makeText(HomeActivity.this, "nothing found", Toast.LENGTH_SHORT).show();
                        reloadData();
                    }
                    return true;

                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        svSearch.setOnCloseListener(new SearchView.OnCloseListener()

        {
            @Override
            public boolean onClose() {
                reloadData();
                return true;
            }
        });

    }

    private void reloadData() {
        generateCategories();
        adapter.refreshLibraryList(libraryList);
    }

    public void generateCategories() {
        libRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                libraryList.clear();
                pos = 0;
                if (dataSnapshot.hasChildren()) {
                    if (dataSnapshot.child("Android").hasChildren()) {
                        for (DataSnapshot snapshot : dataSnapshot.child("Android").getChildren()) {
                            libraryList.add(new LibraryModel(snapshot, "Android"));
                            adapter.notifyItemInserted(pos++);
                        }
                    }
                    if (dataSnapshot.child("Javascript").hasChildren()) {
                        for (DataSnapshot snapshot : dataSnapshot.child("Javascript").getChildren()) {
                            libraryList.add(new LibraryModel(snapshot, "Javascript"));
                            adapter.notifyItemInserted(pos++);
                        }
                    }
                    if (dataSnapshot.child("Python").hasChildren()) {
                        for (DataSnapshot snapshot : dataSnapshot.child("Python").getChildren()) {
                            libraryList.add(new LibraryModel(snapshot, "Python"));
                            adapter.notifyItemInserted(pos++);
                        }
                    }
                }
                pbStatus.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (databaseError != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this)
                            .setMessage("Could Not fetch Details " + databaseError.getMessage())
                            .setIcon(R.drawable.library)
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(HomeActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.create().show();
                }
                pbStatus.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Setting_slide) {
            Intent Setting_SlideIntent = new Intent(HomeActivity.this, SettingSubSettingActivity.class);
            startActivity(Setting_SlideIntent);
        } else if (id == R.id.Feedback_Slide) {
            Intent Feeback_SlideIntent = new Intent(HomeActivity.this, Feedback.class);
            startActivity(Feeback_SlideIntent);

        } else if (id == R.id.About_slide) {
            Intent About_SlideIntent = new Intent(HomeActivity.this, About.class);
            startActivity(About_SlideIntent);

        } else if (id == R.id.Profile_id) {
            Intent pro = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(pro);
        } else if (id == R.id.Logout_slide) {
            FirebaseAuth.getInstance().signOut();
            //  mAuth.signOut();
            try {
                LoginManager.getInstance().logOut();
                AccessToken.setCurrentAccessToken(null);
            } catch (Exception ignored) {

            }
            Intent lgtIntent = new Intent(HomeActivity.this, RegisterCodeBay.class);
            startActivity(lgtIntent);
            finish();

        } else if (id == R.id.nav_send) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, "CodeBay");

            share.putExtra(Intent.EXTRA_TEXT, "Your friend has invited you to join the app./n To join click the link");
            startActivity(Intent.createChooser(share, "Share via..."));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
