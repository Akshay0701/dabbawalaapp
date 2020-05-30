package com.example.dabbaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.dabbaapp.dabbawala.Dabawalaregister;
import com.example.dabbaapp.dabbawala.Dabbawalalogin;
import com.example.dabbaapp.user.UserLogin;

public class MainActivity extends AppCompatActivity {




    ImageView userIv,dabaWalaIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userIv=findViewById(R.id.userIv);
        dabaWalaIv=findViewById(R.id.dabaWalaIv);

        userIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user Login
                Intent intent=new Intent(MainActivity.this, UserLogin.class);
                startActivity(intent);
            }
        });

        dabaWalaIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dabawala login
                Intent intent=new Intent(MainActivity.this, Dabawalaregister.class);
                startActivity(intent);

            }
        });
    }
}
