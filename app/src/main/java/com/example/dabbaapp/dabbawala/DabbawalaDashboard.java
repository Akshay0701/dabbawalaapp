package com.example.dabbaapp.dabbawala;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dabbaapp.MainActivity;
import com.example.dabbaapp.R;
import com.example.dabbaapp.user.AboutUs;
import com.example.dabbaapp.user.DashBoradActivity;
import com.example.dabbaapp.user.User_Account;
import com.example.dabbaapp.user.User_Home;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DabbawalaDashboard extends AppCompatActivity {

    TextView actionBar;

    FirebaseAuth firebaseAuth;

    String mUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dabbawala_dashboard);

        actionBar=findViewById(R.id.txt);


        firebaseAuth= FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView=findViewById(R.id.navigation);
        //event listner
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);

        //init default fragment
        actionBar.setText("Order");
        DabaOrder fragment1=new DabaOrder();
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
                        case R.id.daba_order:
                            //home fragmentation

                            actionBar.setText("Home Page");
                            DabaOrder fragment1=new DabaOrder();
                            FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.content1,fragment1,"");
                            ft1.commit();
                            return true;
                        case R.id.daba_account:
                            //profile fargment transcatrion

                            actionBar.setText("Account");
                            DabaAccount fragment2=new DabaAccount();
                            FragmentTransaction ft2=getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content1,fragment2,"");
                            ft2.commit();
                            return true;
                        case R.id.asdas:
                            //user fragmentation

                            actionBar.setText("About Us");
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

        }
    }

}
