package com.example.agudajce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class AboutUsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private boolean admin_mode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            setAdmin_mode( (boolean) extras.get("Admin_Mode"));

        }
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
        getMenuInflater().inflate(R.menu.about_us, menu);
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
        } else if (id == R.id.nav_event) {
            finish();
            openEvents();

        } else if (id == R.id.nav_login) {
            finish();
            openLogin();

        }else if(id == R.id.nav_marathon){
            finish();
            openMarathon();
        }else if(id == R.id.nav_sign_out){
            setAdmin_mode(false);
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra("Admin_Mode", isAdmin_mode());
            finish();
            startActivity(intent);
        } else if(id == R.id.nav_admin_panel){
            finish();
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

    public  void openMarathon() {
        Intent intent = new Intent(getBaseContext(), MarathonsActivity.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }

    public boolean isAdmin_mode() {
        return admin_mode;
    }

    public void setAdmin_mode(boolean admin_mode) {
        this.admin_mode = admin_mode;
    }
}
