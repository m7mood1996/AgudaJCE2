package com.example.agudajce;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminPanelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private boolean admin_mode = false;
    private final int PICK_IMAGE_REQUEST = 71;
    Button submet_changes,delete_member,btnchoose,btnAddMember;
    ImageView imageChoosen;
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    Spinner spinner;
    Uri filePath;
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

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        spinner = findViewById(R.id.spannerdlt);
        AgudaMembertoDelete();
        imageChoosen = findViewById(R.id.phoChoosen);
        delete_member = findViewById(R.id.delete_member);
        delete_member.setOnClickListener(this);
        btnchoose = findViewById(R.id.choosePho);
        btnchoose.setOnClickListener(this);
        btnAddMember = findViewById(R.id.btnAddMember);
        btnAddMember.setOnClickListener(this);
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
                updateFireBase();
                break;
            case R.id.delete_member:
                deleteAgudaMember();
                break;
            case R.id.choosePho:
                getPhotofromphone();
                break;

            case R.id.btnAddMember:
                AddMemberHandler();
                break;

            default:
                break;
        }

    }

    public void updateFireBase(){
        TextView textView;

        textView = (TextView)findViewById(R.id.NewNumber) ; //new phone number
        String newPhoneNum =  textView.getText().toString();

        updatePhoneNum(newPhoneNum);

        textView = (TextView)findViewById(R.id.NewEmail) ;  // new email
        String newEmail = textView.getText().toString();

        updateEmail(newEmail);


        textView = (TextView)findViewById(R.id.NewSOpen) ;  // reshon day
        String reshon_opening =textView.getText().toString(); // opening
        textView = (TextView)findViewById(R.id.NewSClosing) ;//closing
        String reshon_closing =textView.getText().toString();

        updateSday(reshon_opening,reshon_closing,"contactUsPage/officeOpenTime/rishon");

        textView = (TextView)findViewById(R.id.newMOpening) ;  // shenyy day
        String sheny_opening =textView.getText().toString(); // opening
        textView = (TextView)findViewById(R.id.newMClosing) ;//closing
        String sheny_colsing =textView.getText().toString();

        updateSday(sheny_opening,sheny_colsing,"contactUsPage/officeOpenTime/sheni");

        textView = (TextView)findViewById(R.id.newTOpening) ;  // shlyshy day
        String shlyshy_opening =textView.getText().toString(); // opening
        textView = (TextView)findViewById(R.id.newTClosing) ;//closing
        String shlyshy_closing =textView.getText().toString();

        updateSday(shlyshy_opening,shlyshy_closing,"contactUsPage/officeOpenTime/shlishi");

        textView = (TextView)findViewById(R.id.newWOpening) ;  // reveey day
        String reveey_opening =textView.getText().toString(); // opening
        textView = (TextView)findViewById(R.id.newWClosing) ;//closing
        String reveey_closing =textView.getText().toString();

        updateSday(reveey_opening,reveey_closing,"contactUsPage/officeOpenTime/revii");

        textView = (TextView)findViewById(R.id.newT2Opening) ;  // hamshy day
        String hamshy_opening =textView.getText().toString(); // opening
        textView = (TextView)findViewById(R.id.newT2Closing) ;//closing
        String hamshy_closing =textView.getText().toString();


        updateSday(hamshy_opening,hamshy_closing,"contactUsPage/officeOpenTime/hamishi");





    }

    public void updateSday(String open, String close, String to){


        if(open.isEmpty() == true && close.isEmpty() == false){


            Toast.makeText(this,"one of opening or closing is empty please fill them",Toast.LENGTH_LONG).show();
        }

        else if(open.isEmpty() == false && close.isEmpty() == true){


            Toast.makeText(this,"one of opening or closing is empty please fill them",Toast.LENGTH_LONG).show();
        }
        else if(open.isEmpty() == false && close.isEmpty() == false){
            boolean submit = findIfTime(open,close);
            if(submit == true)
                updatedayTime(to,open + " - "+ close);

        }



    }
    public boolean findIfTime(String open,String close){

        DateFormat formatter = new SimpleDateFormat("hh:mm");
        try {
            Date date = formatter.parse(open);
            Date date2 = formatter.parse(close);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
           return false;
        }



    }

    public void updateEmail(String newEmail){


        if(newEmail.isEmpty() == false && newEmail.contains("@") == true){



            myRef.child("contactUsPage/officeEmail").setValue(newEmail);
            System.out.println("hello\tnewemail\t"+newEmail);

        }
        else if (newEmail.isEmpty() == false){

            Toast.makeText(this,"Error in Email please provide E-mail with @ asign",Toast.LENGTH_LONG).show();

        }


    }

    public void updatePhoneNum(String newPhoneNum){

        if(newPhoneNum.isEmpty() == false){

            myRef.child("contactUsPage/officeNumber").setValue(newPhoneNum);
            System.out.println("hello\tnewNumber\t"+newPhoneNum);

        }

    }

    public void updatedayTime(String to,String newTime){

        myRef.child(to).setValue(newTime);

    }

    public void AgudaMembertoDelete(){
        myRef.child("aboutUsPage").child("agudaOfficeProfessionalSkills").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> namelist = new ArrayList<String>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Getting the data from snapshot
                    String name = (String) postSnapshot.child("name").getValue();
                    namelist.add(name);


                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AdminPanelActivity.this, android.R.layout.simple_spinner_item, namelist);
                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // attaching data adapter to spinner
                    spinner.setAdapter(dataAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public void deleteAgudaMember(){


        myRef.child("aboutUsPage").child("agudaOfficeProfessionalSkills").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = spinner.getSelectedItem().toString();
                System.out.println("mahmood text\t"+ text);
                String id = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //Getting the data from snapshot
                    String name = (String) postSnapshot.child("name").getValue();
                    System.out.println("mahmood name\t\t "+ name);
                    if (name == text){
                        id = postSnapshot.getKey();
                        System.out.println("mahmood id\t"+ id);

                    }
                }
                myRef.child("aboutUsPage").child("agudaOfficeProfessionalSkills").child(id).setValue(null);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    public void getPhotofromphone(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture") ,PICK_IMAGE_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null ){
            filePath = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageChoosen.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();

            }

        }

    }

    public void AddMemberHandler(){
        TextView name;
        TextView job;
        TextView jobE;

        name = findViewById(R.id.newname);
        job = findViewById(R.id.job);
        jobE = findViewById(R.id.jobE);

        String nameString = name.getText().toString();
        String  jobString = job.getText().toString();
        String jobEString = jobE.getText().toString();

        if(nameString.isEmpty() == true){

        }





    }
}
