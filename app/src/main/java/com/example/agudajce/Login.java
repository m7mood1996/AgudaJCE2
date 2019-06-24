package com.example.agudajce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    String skarim = "";
    String marathon = "";
    private boolean admin_mode = false;
    TextView username;
    TextView password;
    TextView usernameError;
    TextView PasswordError;
    Button signin;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getValue();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            setAdmin_mode((boolean) extras.get("Admin_Mode"));

        }
        if(isAdmin_mode() == true){


            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_admin_panel).setVisible(true);
            nav_Menu.findItem(R.id.nav_sign_out).setVisible(true);


        }


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
        firebaseAuth = FirebaseAuth.getInstance();


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
        } else if (id == R.id.nav_contactus) {
            finish();
            openContactUs();

        } else if (id == R.id.nav_login) {


        } else if (id == R.id.nav_about) {
            finish();
            openAboutus();

        } else if (id == R.id.nav_marathon) {
            finish();
            openMarathon();
        }else if (id == R.id.skarim) {
            finish();
            openSkarim();
        }else if(id == R.id.nav_event){  //should refactor to nav_album
            finish();
            openAlbum();
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


    public  void openAlbum() {

        Intent intent = new Intent(getBaseContext(), AlbumActivity.class);
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


    public  void openAboutus() {

        Intent intent = new Intent(getBaseContext(), AboutUsActivity.class);
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }
    public  void openMarathon() {


        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(marathon));
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }
    public  void openSkarim() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(skarim));
        intent.putExtra("Admin_Mode", isAdmin_mode());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.signin_btn){
            fieldsErrors();


        }
    }
    public void fieldsErrors(){ // if username or password are illegal inputs and connect to firebase and find if username and password are correct
        String usr =username.getText().toString();
        String pass = password.getText().toString();
        System.out.println(" fuck yeah"+(username.getText().toString().length()));
        int indexx = usr.indexOf("@");

        if(usr == null || usr.length() == 0)
            usernameError.setText("* Incorrect! E-mail is empty");
        else if(indexx == -1) {
            usernameError.setText("* Incorrect! E-mail must contain @ sign");
        }
        if(pass == null || pass.length() == 0)
            PasswordError.setText("* Incorrect! Password is empty");


        else {
            firebaseAuth.signInWithEmailAndPassword(usr, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        setAdmin_mode(true);

                        finishAffinity(); // finish all the previose activities

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("Admin_Mode", true);
                        startActivity(intent);

                        Intent intent1 = new Intent(getBaseContext(), AdminPanelActivity.class);
                        intent1.putExtra("Admin_Mode", true);
                        startActivity(intent1);

                    } else {
                        usernameError.setText("");
                        PasswordError.setText("");
                        Toast.makeText(getApplicationContext(), "E-mail or password is wrong", Toast.LENGTH_SHORT).show();
                    }
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
