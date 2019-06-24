package com.example.agudajce;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
public class AboutUsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener{
    FirebaseDatabase database;
    FirebaseDatabase database1 = FirebaseDatabase.getInstance();
    DatabaseReference ref = database1.getReference();
    String skarim = "";
    String marathon = "";
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private AboutUsRecycle aboutUsRecycle;
    TextView yor;
    TextView syor;
    TextView gizbarut;
    TextView office;
    TextView dover;
    TextView academy;
    TextView populog;
    TextView revaha;
    TextView tarbut;
    TextView sport;
    TextView hackathon;
    Context contex = this;


    private boolean admin_mode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getValue();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ////Define the TextView of the aguda members's email
        yor = findViewById(R.id.yor);
        syor = findViewById(R.id.syor);
        gizbarut = findViewById(R.id.gizbarut);
        office = findViewById(R.id.office);
        dover = findViewById(R.id.dover);
        academy = findViewById(R.id.academy);
        populog = findViewById(R.id.populog);
        revaha = findViewById(R.id.revaha);
        tarbut = findViewById(R.id.tarbut);
        sport = findViewById(R.id.sport);
        hackathon = findViewById(R.id.hackathon);
        yor.setOnClickListener(this);
        syor.setOnClickListener(this);
        gizbarut.setOnClickListener(this);
        office.setOnClickListener(this);
        dover.setOnClickListener(this);
        academy.setOnClickListener(this);
        populog.setOnClickListener(this);
        revaha.setOnClickListener(this);
        tarbut.setOnClickListener(this);
        sport.setOnClickListener(this);
        hackathon.setOnClickListener(this);
        /////////////////////////////
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        database = FirebaseDatabase.getInstance();

        prepareData();


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
        } else if (id == R.id.nav_contactus) {
            finish();
            openContactUs();
        } else if (id == R.id.nav_event) {
            finish();
            openEvents();

        } else if (id == R.id.nav_login) {
            finish();
            openLogin();

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

    public void openLogin() {

        Intent intent = new Intent(getBaseContext(), Login.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }

    public void openContactUs() {

        Intent intent = new Intent(getBaseContext(), ContactUsActivity.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }

    public void openEvents() {

        Intent intent = new Intent(getBaseContext(), AlbumActivity.class);
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


    private void prepareData() {


        DatabaseReference myRef = database.getReference().child("aboutUsPage").child("agudaOfficeProfessionalSkills");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<CycleObject> recyclObjectList = new ArrayList<>();
                layoutManager = new LinearLayoutManager(contex, LinearLayoutManager.HORIZONTAL, false);

                recyclerView = findViewById(R.id.recycler_View);



                aboutUsRecycle = new AboutUsRecycle(recyclObjectList, contex);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(aboutUsRecycle);
                updateDate(dataSnapshot ,  recyclObjectList);
                System.out.println("wow\t" + recyclObjectList);
                aboutUsRecycle.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void  updateDate(DataSnapshot dataSnapshot ,  ArrayList<CycleObject> recyclObjectList){

        for(DataSnapshot ds: dataSnapshot.getChildren()){
            CycleObject cycleObject = new CycleObject();
            cycleObject.setDes((String) ds.child("name").getValue());
            cycleObject.setImageURL((String)ds.child("imageUrl").getValue() );
            cycleObject.setSkill((String)ds.child("profession").getValue());
            System.out.println("shalom\t"+ cycleObject);
            recyclObjectList.add(cycleObject);
            aboutUsRecycle.notifyDataSetChanged();

            //System.out.println("shalom\t"+ cycleObject.getImageURL());
        }


    }

    @Override
    public void onClick(View v) {
        Intent mail;
        switch (v.getId()){
            case R.id.yor :
                mail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","yor.jce@gmail.com",null));
                startActivity(Intent.createChooser(mail, "Choose an Email client :"));
                break;
            case R.id.syor :
                mail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","syor.jce@gmail.com",null));
                startActivity(Intent.createChooser(mail, "Choose an Email client :"));
                break;
            case R.id.gizbarut :
                mail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","gizbarut.jce@gmail.com",null));
                startActivity(Intent.createChooser(mail, "Choose an Email client :"));
                break;
            case R.id.office :
                mail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","office.jce@gmail.com",null));
                startActivity(Intent.createChooser(mail, "Choose an Email client :"));
                break;
            case R.id.dover :
                mail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","dover.jce@gmail.com",null));
                startActivity(Intent.createChooser(mail, "Choose an Email client :"));
                break;
            case R.id.academy :
                mail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","academy.jce@gmail.com",null));
                startActivity(Intent.createChooser(mail, "Choose an Email client :"));
                break;
            case R.id.populog :
                mail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","populog.jce@gmail.com",null));
                startActivity(Intent.createChooser(mail, "Choose an Email client :"));
                break;
            case R.id.revaha :
                mail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","revaha.jce@gmail.com",null));
                startActivity(Intent.createChooser(mail, "Choose an Email client :"));
                break;
            case R.id.tarbut :
                mail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","tarbut.jce@gmail.com",null));
                startActivity(Intent.createChooser(mail, "Choose an Email client :"));
                break;
            case R.id.sport :
                mail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","sport.jce@gmail.com",null));
                startActivity(Intent.createChooser(mail, "Choose an Email client :"));
                break;
            case R.id.hackathon :
                mail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","hackathon.jce@gmail.com",null));
                startActivity(Intent.createChooser(mail, "Choose an Email client :"));
                break;
            default:
                break;
        }
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
