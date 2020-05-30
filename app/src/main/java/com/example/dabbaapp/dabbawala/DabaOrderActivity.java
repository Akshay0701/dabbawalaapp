package com.example.dabbaapp.dabbawala;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dabbaapp.R;
import com.example.dabbaapp.user.order_daba;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DabaOrderActivity extends AppCompatActivity {


    //recycle view
    RecyclerView postsRecycleView;

    FirebaseAuth firebaseAuth;

    SharedPreferences.Editor editor;
    String email;


    //profile
    TextView dabawla_name,dabawla_location,dabawla_phone,dabawla_special;

    String uid,hisId;

    Button order_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);

        Intent intent=getIntent();
        hisId=intent.getStringExtra("hisId");

        dabawla_name=findViewById(R.id.username);
        dabawla_location=findViewById(R.id.location);
        dabawla_special=findViewById(R.id.vegOrnonveg);
        dabawla_phone=findViewById(R.id.phoneNo);

        order_id=findViewById(R.id.order_id);



        //data base
        Query query= FirebaseDatabase.getInstance().getReference("Dabbawala").orderByChild("uid").equalTo(hisId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String username=ds.child("Name").getValue().toString();
                    String location=ds.child("location").getValue().toString();
                    String specidal=ds.child("special").getValue().toString();
                    String phone=ds.child("PhoneNO").getValue().toString();
                   email =ds.child("Email").getValue().toString();



                    dabawla_name.setText("Name : "+username);
                    dabawla_location.setText("location : "+location);
                    dabawla_special.setText("special : "+specidal);
                    dabawla_phone.setText("Phone NO : "+phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        order_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle button
                Intent intent1=new Intent(DabaOrderActivity.this, order_daba.class);
                intent1.putExtra("hisId",hisId);
                intent1.putExtra("email",email);
                startActivity(intent1);

            }
        });

    }






    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
