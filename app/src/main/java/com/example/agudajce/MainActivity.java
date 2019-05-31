package com.example.agudajce;

import android.content.Intent;

import android.net.Uri;

import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.widget.Button;


import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private List<Post> postList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String accessToken = "EAAF8xbp4sd4BAF19WG7BAQ7Kwo0vtzTDfYFroM2MiLIW5y0x9fhyhZAKFUkzpCRdOyZAXMKDklQN1IZCxImr5jzT28zkPGWKu2DRIVDZBlSB8drk9FWCcINiRMJez48edHp84P6mZBJSN3jCn33AWrrud1sFfk246v43xQbPfsEVRoSZAIsyirOAoHloNQmkNmZCspOnsZAdcQZDZD";
        String appId = "418663655584222";
        String userId = "2350432641906028";
        Collection<String> permissions = new ArrayList<>();
        permissions.add("manage_pages");
        AccessToken token = new AccessToken(accessToken, appId, userId,  null, null, null, null, null, null,null);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Button fb = findViewById(R.id.fb_logo);
        Button insta = findViewById(R.id.insta_logo);
        Button dropbox = findViewById(R.id.drop_logo);

        fb.setOnClickListener(this);
        insta.setOnClickListener(this);
        dropbox.setOnClickListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MyAdapter(postList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        GraphRequest request = GraphRequest.newGraphPathRequest(
                token,
                "/1087780278090610/posts",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                       JSONObject jsonObject = response.getJSONObject();

                        prepareData(jsonObject);


                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "full_picture,message");
        request.setParameters(parameters);
        request.executeAsync();




     //   prepareData();
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






    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_post) {
            openPost();

        } else if (id == R.id.nav_event) {
            openEvents();
        } else if (id == R.id.nav_login) {

        } else if (id == R.id.nav_about) {
            openAboutus();
        } else if (id == R.id.nav_marathon) {
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

    public  void openEvents() {
        Intent i = new Intent(this, EventsActivity.class);
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


    @Override
    public void onClick(View v) {
        Intent brows;
        switch (v.getId()){

            case R.id.fb_logo:
                brows= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/aguda.jce/"));
                startActivity(brows);
                break;
            case R.id.insta_logo:
                brows = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/aguda.jce?igshid=1roen5wayrk79"));
                startActivity(brows);

                break;
            case R.id.drop_logo:
                brows = new Intent(Intent.ACTION_VIEW, Uri.parse("http://dropagudajce.co.il"));
                startActivity(brows);

                break;

            default:
                break;
        }

    }

    private void prepareData(JSONObject jsonObject)  {

       Worker worker = new Worker(jsonObject);
        worker.start();

    }

    private class Worker extends Thread {
        JSONObject jsonObject;
        public Worker(JSONObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        public void run() {



                // facebook.getAlbum("1087780278090610");


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    for(int i =0;i<5;i++){

                        try {
                        String descri =(jsonObject.getJSONArray("data")).getJSONObject(i).getString("message");
                        String pic = jsonObject.getJSONArray("data").getJSONObject(i).getString("full_picture");
                            Post post = new Post(descri,pic);
                            postList.add(post);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }


                    mAdapter.notifyDataSetChanged();
                }
            });


        }
    }

}
