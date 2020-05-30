package com.example.dabbaapp.dabbawala;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dabbaapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DabaOrderDetails extends AppCompatActivity {


    String uid, dabalwalaId;

    Button callBtn;

    DatabaseReference reference;

    String User_Phone;
    TextView timeslot, user_daba_size, user_food_type, user_quantity, user_address, user_name, user_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daba_order_details);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        dabalwalaId = intent.getStringExtra("dabawalaId");

        timeslot = findViewById(R.id.timing);
        user_daba_size = findViewById(R.id.user_daba_size);
        user_food_type = findViewById(R.id.user_food_type);
        user_quantity = findViewById(R.id.user_quantity);
        user_address = findViewById(R.id.user_address);
        user_name = findViewById(R.id.user_name);
        user_phone = findViewById(R.id.user_phone);
        callBtn = findViewById(R.id.callbtn);

        Query query = FirebaseDatabase.getInstance().getReference("Order").child(dabalwalaId).orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String dabaSize = ds.child("Dabba_Size").getValue().toString();
                    String foodType = ds.child("Food_Type").getValue().toString();
                    String Quantity = ds.child("Quantity").getValue().toString();
                    String User_Address = ds.child("User_Address").getValue().toString();
                    String User_Name = ds.child("User_Name").getValue().toString();
                    User_Phone = ds.child("User_Phone").getValue().toString();
                    String timing = ds.child("Timing").getValue().toString();

                    timeslot.setText(timing);
                    user_daba_size.setText(dabaSize);
                    user_food_type.setText(foodType);
                    user_quantity.setText(Quantity);
                    user_address.setText(User_Address);
                    user_name.setText(User_Name);
                    user_phone.setText(User_Phone);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String posted_by = User_Phone;

                String uri = "tel:" + posted_by.trim();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    new AlertDialog.Builder(DabaOrderDetails.this,4)
                            .setTitle("Required Location Permission")
                            .setMessage("You have to give this permission to acess this feature")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(DabaOrderDetails.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            1);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create()
                            .show();
                    return;
                }
                startActivity(intent);
            }
        });

    }
}
