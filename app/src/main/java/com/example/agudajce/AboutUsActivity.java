package com.example.agudajce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "AboutUsActivity";
    //public static final String Database_Path = "All_Image_Uploads_Database";
    //private ArrayList<String> mTexts = new ArrayList<>();
    //private ArrayList<String> mImages = new ArrayList<>();

   ////////example code

    ArrayList<Data> list = new ArrayList<>();
  /*  RecyclerView recyclerView;
    DatabaseReference databaseReference;
    RecyclerView.Adapter adapter ;

    */
    ///////example code
    private boolean admin_mode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ///////example code
        /*
        recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AboutUsActivity.this));
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    CycleObject imageUploadInfo = postSnapshot.getValue(CycleObject.class);

                    list.add(imageUploadInfo);
                }

                adapter = new AboutUsRecycle(getApplicationContext(), list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


            */
        ///////example code
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

        getImages();
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
    public  void openContactUs() {

        Intent intent = new Intent(getBaseContext(), ContactUsActivity.class);
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


    private void getImages(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("aboutUsPage").child("agudaOfficeProfessionalSkills");



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //In this case, "shalom" will be stored in mName



                    Data data = snapshot.getValue(Data.class);
                    list.add(data);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        System.out.println("hellohello iti\t" + list.get(0).getImageUrl());
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
/*
        mImages.add(name);
        mTexts.add(img);

        mImages.add("");
        mTexts.add(name);

        mImages.add("");
        mTexts.add(name);

        mImages.add("");
        mTexts.add("Anas");
*/
        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(layoutManager);
        //AboutUsRecycle adapter = new AboutUsRecycle(this, mTexts, mImages);
        //ecyclerView.setAdapter(adapter);
    }

    public void getDataFromFireBase(DatabaseReference myRef , String from, final int id){

        myRef.child(from).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(id == R.id.textimg) {
                    TextView text = (TextView) findViewById(id);
                    text.setText((String) dataSnapshot.getValue());

                }

                if(id == R.id.image_view){
                    CircleImageView img = (CircleImageView)findViewById(R.id.image_view);
                    img.setImageURI(Uri.parse((String) dataSnapshot.getValue()));

                    System.out.println("hellohello itaaaaai\t" +(String) dataSnapshot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
public class Data{
        private String imageUrl;
        private String namee;
        private String profession;

    public Data(String imageUrl, String namee, String profession) {
        this.imageUrl = imageUrl;
        this.namee = namee;
        this.profession = profession;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNamee() {
        return namee;
    }

    public void setNamee(String namee) {
        this.namee = namee;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}


}
