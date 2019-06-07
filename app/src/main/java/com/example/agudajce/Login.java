package com.example.agudajce;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    TextView username;
    TextView password;
    TextView usernameError;
    TextView PasswordError;
    Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        username = findViewById(R.id.editUsername);
        password= findViewById(R.id.editPassward);
        usernameError = findViewById(R.id.showErrorForUser);
        PasswordError = findViewById(R.id.showErrorForPass);

        signin = findViewById(R.id.signin_btn);
        signin.setOnClickListener(this);


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
        getMenuInflater().inflate(R.menu.login, menu);
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

        }else if(id == R.id.nav_event){  //should refactor to nav_album
            finish();
            openAlbum();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public  void openPost() {
        Intent i = new Intent(this, PostsActivity.class);
        startActivity(i);
    }
    public  void openAlbum() {
        Intent i = new Intent(this, AlbumActivity.class);
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
        if(v.getId() == R.id.signin_btn){
            fieldsErrors();


        }
    }
    public boolean fieldsErrors(){ // if username or password are illegal inputs
        String usr =username.getText().toString();
        String pass = password.getText().toString();
        System.out.println(" fuck yeah"+(username.getText().toString().length()));
        int indexx = usr.indexOf("@");

        if(usr == null || usr.length() == 0)
            usernameError.setText("* Incorrect! email is empty");
        else if(indexx == -1) {
            usernameError.setText("* Incorrect! email must contain @ sign");
        }
        if(pass == null || pass.length() == 0)
            PasswordError.setText("* Incorrect! Password is empty");


        return false;
    }

}
