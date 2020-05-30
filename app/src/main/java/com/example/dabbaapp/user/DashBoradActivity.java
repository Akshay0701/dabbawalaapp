package com.example.dabbaapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dabbaapp.MainActivity;
import com.example.dabbaapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashBoradActivity extends AppCompatActivity {



   Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    TextView txt;
    String mUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_borad);

        txt=findViewById(R.id.txt);
        toolbar=findViewById(R.id.toolbar);



        firebaseAuth= FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView=findViewById(R.id.navigation);
        //event listner
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);


        User_Home fragment1=new User_Home();
        txt.setText("Home");
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content1,fragment1,"");
        ft1.commit();


        //checking login
        checkforuserlogin();
    }



    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.user_home:
                            //home fragmentation
                            txt.setText("Home");
                            User_Home fragment1=new User_Home();
                            FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.content1,fragment1,"");
                            ft1.commit();
                            return true;
                        case R.id.user_account:
                            //profile fargment transcatrion
                            txt.setText("Profile");
                            User_Account fragment2=new User_Account();
                            FragmentTransaction ft2=getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content1,fragment2,"");
                            ft2.commit();
                            return true;
                        case R.id.asdas:
                            //user fragmentation
                            txt.setText("About");
                            AboutUs fragment3=new AboutUs();
                            FragmentTransaction ft3=getSupportFragmentManager().beginTransaction();
                            ft3.replace(R.id.content1,fragment3,"");
                            ft3.commit();
                            return true;

                    }

                    return false;
                }
            };

    private void checkforuserlogin() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {

            mUID=user.getUid();




        }
        else{
            startActivity(new Intent(DashBoradActivity.this, MainActivity.class));
            finish();
        }
    }
    @Override
    protected void onStart() {
        checkforuserlogin();
        super.onStart();
    }
}
