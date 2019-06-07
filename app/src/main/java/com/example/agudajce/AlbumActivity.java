package com.example.agudajce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AlbumActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Gallery_row> gallery = new ArrayList<>();
    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String accessToken = getString(R.string.accessToken);;
        String appId = getString(R.string.appId);
        String userId = getString(R.string.userId);
        Collection<String> permissions = new ArrayList<>();
        permissions.add("manage_pages");
        AccessToken token = new AccessToken(accessToken, appId, userId,  null, null, null, null, null, null,null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.gallery_recycler_view);

        galleryAdapter = new GalleryAdapter(gallery);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(galleryAdapter);

        GraphRequest request = GraphRequest.newGraphPathRequest(
                token,
                "/1095717480630223",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        JSONObject jsonObject = response.getJSONObject();

                        prepareData(jsonObject);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "photos{images}");
        request.setParameters(parameters);
        request.executeAsync();




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.album, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            finish();
        } else if (id == R.id.nav_post) {
            finish();
            openPost();

        } else if (id == R.id.nav_login) {


        } else if (id == R.id.nav_about) {
            finish();
            openAboutus();

        } else if (id == R.id.nav_marathon) {
            finish();
            openMarathon();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void openPost() {
        Intent i = new Intent(this, PostsActivity.class);
        startActivity(i);
    }


    public  void openAboutus() {
        Intent i = new Intent(this, AboutUsActivity.class);
        startActivity(i);
    }
    public  void openMarathon() {
        Intent i = new Intent(this, MarathonsActivity.class);
        startActivity(i);
    }

    void prepareData(JSONObject jsonObject){
        Worker_T worker = new Worker_T(jsonObject);
        worker.start();

    }




    private class Worker_T extends Thread {
        JSONObject jsonObject;
        public Worker_T(JSONObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        public void run() {



            // facebook.getAlbum("1087780278090610");


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    for(int i =0;i<20;i+=2){

                        try {

                            String firstImage =jsonObject.getJSONObject("photos").getJSONArray("data").getJSONObject(i).getJSONArray("images").getJSONObject(0).getString("source");
                            String secondeImage =jsonObject.getJSONObject("photos").getJSONArray("data").getJSONObject(i+1).getJSONArray("images").getJSONObject(0).getString("source");
                            System.out.println(firstImage+"+"+ secondeImage);
                            Gallery_row gallery_row = new Gallery_row(firstImage,secondeImage);
                            gallery.add(gallery_row);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }


                    galleryAdapter.notifyDataSetChanged();
                }
            });


        }
    }



}