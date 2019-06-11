package com.example.agudajce;

import android.content.Intent;

import android.net.Uri;

import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Menu;
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

import com.facebook.GraphRequest;

import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.Collection;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private boolean admin_mode = false;
    private List<Post> postList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String accessToken = getString(R.string.accessToken);;
        String appId = getString(R.string.appId);
        String userId = getString(R.string.userId);
        Collection<String> permissions = new ArrayList<>();
        permissions.add("manage_pages");
        AccessToken token = new AccessToken(accessToken, appId, userId,  null, null, null, null, null, null,null);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            setAdmin_mode((boolean) extras.get("Admin_Mode"));

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        if(isAdmin_mode() == true){


            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_admin_panel).setVisible(true);
            nav_Menu.findItem(R.id.nav_sign_out).setVisible(true);
            nav_Menu.findItem(R.id.nav_login).setVisible(false);

        }
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
            openLogin();

        } else if (id == R.id.nav_about) {
            openAboutus();
        } else if (id == R.id.nav_marathon) {
            openMarathon();
        } else if(id == R.id.nav_sign_out){
            setAdmin_mode(false);
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra("Admin_Mode", isAdmin_mode());
            finish();
            startActivity(intent);
        } else if(id == R.id.nav_admin_panel){

            openAdminPanel();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void  openAdminPanel(){
        Intent intent = new Intent(getBaseContext(), AdminPanelActivity.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);

    }
    public void openLogin(){

        Intent intent = new Intent(getBaseContext(), Login.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);

    }
    public  void openPost() {

        Intent intent = new Intent(getBaseContext(), PostsActivity.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);

    }

    public  void openEvents() {
        Intent intent = new Intent(getBaseContext(), AlbumActivity.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);

    }
    public  void openAboutus() {

        Intent intent = new Intent(getBaseContext(), AboutUsActivity.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }
    public  void openMarathon() {
        Intent intent = new Intent(getBaseContext(), MarathonsActivity.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);

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
    public boolean isAdmin_mode() {
        return admin_mode;
    }

    public void setAdmin_mode(boolean admin_mode) {
        this.admin_mode = admin_mode;
    }
}
