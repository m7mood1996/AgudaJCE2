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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminPanelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private boolean admin_mode = false;
    Button submet_changes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_panel);
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


        submet_changes = (Button)findViewById(R.id.submet_changes_Contact_us);
        submet_changes.setOnClickListener(this);
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
        getMenuInflater().inflate(R.menu.admin_panel, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            finish();
        } else if (id == R.id.nav_contactus) {
            finish();
            openContactUs();

        } else if (id == R.id.nav_login) {
            finish();
            openLogin();


        } else if (id == R.id.nav_about) {
            finish();
            openAboutus();

        } else if (id == R.id.nav_marathon) {
            finish();
            openMarathon();

        }else if(id == R.id.nav_sign_out){
            setAdmin_mode(false);
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra("Admin_Mode", isAdmin_mode());
            finishAffinity();
            startActivity(intent);
        } else if(id == R.id.nav_admin_panel){

        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void openLogin(){

        Intent intent = new Intent(getBaseContext(), Login.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }
    public  void openContactUs() {

        Intent intent = new Intent(getBaseContext(), ContactUsActivity.class);
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

    public boolean isAdmin_mode() {
        return admin_mode;
    }

    public void setAdmin_mode(boolean admin_mode) {
        this.admin_mode = admin_mode;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.submet_changes_Contact_us:
                findErrorsInputs();
                break;

            default:
                break;
        }

    }

    public void findErrorsInputs(){
        TextView textView;

        textView = (TextView)findViewById(R.id.newNumber) ; //new phone number
        String newPhoneNum =  textView.getText().toString();

        textView = (TextView)findViewById(R.id.newEmail) ;  // new email
        String newEmail = textView.getText().toString();

        textView = (TextView)findViewById(R.id.newSOpen) ;  // reshon day
        String reshon_opening =textView.getText().toString(); // opening
        textView = (TextView)findViewById(R.id.newSClosing) ;//closing
        String reshon_closing =textView.getText().toString();

        textView = (TextView)findViewById(R.id.newMOpening) ;  // shenyy day
        String sheny_opening =textView.getText().toString(); // opening
        textView = (TextView)findViewById(R.id.newMClosing) ;//closing
        String sheny_colsing =textView.getText().toString();

        textView = (TextView)findViewById(R.id.newTOpening) ;  // shlyshy day
        String shlyshy_opening =textView.getText().toString(); // opening
        textView = (TextView)findViewById(R.id.newTClosing) ;//closing
        String shlyshy_closing =textView.getText().toString();

        textView = (TextView)findViewById(R.id.newWOpening) ;  // reveey day
        String reveey_opening =textView.getText().toString(); // opening
        textView = (TextView)findViewById(R.id.newWClosing) ;//closing
        String reveey_closing =textView.getText().toString();

        textView = (TextView)findViewById(R.id.newT2Opening) ;  // hamshy day
        String hamshy_opening =textView.getText().toString(); // opening
        textView = (TextView)findViewById(R.id.newT2Closing) ;//closing
        String hamshy_closing =textView.getText().toString();


        if(newPhoneNum.isEmpty() == false){

        }
        if(newEmail.isEmpty() == false){

        }
        if(reshon_opening.isEmpty() == false){

        }
        if(reshon_closing.isEmpty() == false){

        }
        if(sheny_opening.isEmpty() == false){

        }
        if(sheny_colsing.isEmpty() == false){

        }
        if(shlyshy_opening.isEmpty() == false){

        }
        if(shlyshy_closing.isEmpty() == false){

        }
        if(reveey_opening.isEmpty() == false){

        }
        if(reveey_closing.isEmpty() == false){

        }
        if(hamshy_opening.isEmpty() == false){

        }
        if(hamshy_closing.isEmpty() == false){

        }







    }
}
