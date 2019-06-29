package com.example.agudajce;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;


public class ContactUsActivity<my_String> extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    String skarim = "";
    String marathon = "";
    private boolean admin_mode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getValue();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            setAdmin_mode((boolean) extras.get("Admin_Mode"));

        }
        if (isAdmin_mode() == true) {


            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_admin_panel).setVisible(true);
            nav_Menu.findItem(R.id.nav_sign_out).setVisible(true);
            nav_Menu.findItem(R.id.nav_login).setVisible(false);

        }

        Button fb = (Button) findViewById(R.id.fb_logo);
        Button insta = (Button)findViewById(R.id.insta_logo);
        Button dropbox = (Button)findViewById(R.id.drop_logo);
        Button massenger = (Button)findViewById(R.id.massenger_logo);
       // Button whatsapp = (Button)findViewById(R.id.whatsapp_logo);
        Button sendmail = (Button)findViewById(R.id.gmail_logo);
        TextView phone =(TextView)findViewById(R.id.phoneNum) ;
        fb.setOnClickListener(this);
        insta.setOnClickListener(this);
        dropbox.setOnClickListener(this);
        massenger.setOnClickListener(this);
      //  whatsapp.setOnClickListener(this);
        sendmail.setOnClickListener(this);
        phone.setOnClickListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        System.out.println("\t\thello bb\t\t");


        fetchDataFromFireBase();
    }

    public void onClick(View v) {
        Intent brows;
        switch (v.getId()) {

            case R.id.fb_logo:
                brows = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/aguda.jce/"));
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
            case R.id.massenger_logo:
                brows = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.messenger.com/t/aguda.jce"));
                startActivity(brows);
                break;

      /*      case R.id.whatsapp_logo:
                brows = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.whatsapp.com"));
                startActivity(brows);
                break;
      */
            case R.id.gmail_logo:
                sendemail();
                break;


            case R.id.phoneNum:
                callPhone();
                break;

            default:
                break;
        }

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
        getMenuInflater().inflate(R.menu.posts, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            finish();
        } else if (id == R.id.nav_event) {
            finish();
            openAlbum();

        } else if (id == R.id.nav_login) {
            finish();
            openLogin();

        } else if (id == R.id.nav_about) {
            finish();
            openAboutus();

        } else if (id == R.id.nav_marathon) {
            finish();
            openMarathon();

        }else if (id == R.id.skarim) {
            finish();
            openSkarim();
        } else if (id == R.id.nav_sign_out) {
            setAdmin_mode(false);
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra("Admin_Mode", isAdmin_mode());
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_admin_panel) {
            finish();
            openAdminPanel();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openAdminPanel() {
        Intent intent = new Intent(getBaseContext(), AdminPanelActivity.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);

    }

    public void openAlbum() {

        Intent intent = new Intent(getBaseContext(), AlbumActivity.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }


    public void openLogin() {

        Intent intent = new Intent(getBaseContext(), Login.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }

    public void openPost() {

        Intent intent = new Intent(getBaseContext(), ContactUsActivity.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }


    public void openAboutus() {

        Intent intent = new Intent(getBaseContext(), AboutUsActivity.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }

    public void openMarathon() {


        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(marathon));
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }
    public  void openSkarim() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(skarim));
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }

    public boolean isAdmin_mode() {
        return admin_mode;
    }

    public void setAdmin_mode(boolean admin_mode) {
        this.admin_mode = admin_mode;
    }

    public void fetchDataFromFireBase() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        // get data about email





        // get data about phone num
        getDataFromFireBase(myRef , "contactUsPage/officeNumber",R.id.phoneNum);


        getDataFromFireBase(myRef , "contactUsPage/officeOpenTime/hamishi",R.id.dayT2);


        getDataFromFireBase(myRef , "contactUsPage/officeOpenTime/revii",R.id.dayW);

        getDataFromFireBase(myRef , "contactUsPage/officeOpenTime/rishon",R.id.dayS);


        getDataFromFireBase(myRef , "contactUsPage/officeOpenTime/sheni",R.id.dayM);


        getDataFromFireBase(myRef , "contactUsPage/officeOpenTime/shishi",R.id.dayF);
        getDataFromFireBase(myRef , "contactUsPage/officeOpenTime/shlishi", R.id.dayT );



    }


    public void sendemail(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("contactUsPage/officeEmail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                //((String) snapshot.getValue());
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "office.jce@gmail.com", null));

                startActivity(Intent.createChooser(intent, "Choose an Email client :"));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void callPhone(){


        TextView phone =(TextView)findViewById(R.id.phoneNum) ;
        String phone_no= phone.getText().toString().replaceAll("-", "");

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    Integer.parseInt("123"));
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+ phone_no)));
        }


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    callPhone();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }


    public void getDataFromFireBase(DatabaseReference myRef , String from, final int ids ){

        myRef.child(from).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                TextView textView = (TextView)findViewById(ids);
                textView.setText(((String) snapshot.getValue()));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
    public void getValue(){
        ref.child("links").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            skarim = (String) dataSnapshot.child("skarim").getValue();
                            marathon = (String) dataSnapshot.child("marathon").getValue();
                            System.out.println("Let me see you friend\t" + skarim);
                            System.out.println("Allahu Akbar \t" + marathon);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(" it's null.");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}

