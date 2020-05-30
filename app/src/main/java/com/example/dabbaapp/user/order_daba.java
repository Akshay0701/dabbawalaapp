package com.example.dabbaapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dabbaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class order_daba extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    //process dialog
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Order");


    Button orderFood;

    TextView name,address,phone;

    CheckBox veg,nonveg;

    String typefood,dabatype,timing,quantity;

    FirebaseUser user;


    //dabawala info
    String dabawlaUid;
    String Dusername;
    String Dlocation;
    String Dspecidal;
    String Dphone;

    String email;


    Spinner spinner,spinner2,spinner3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_daba);

          Intent intent=getIntent();
          dabawlaUid=intent.getStringExtra("hisId");
          email=intent.getStringExtra("email");

          mAuth=FirebaseAuth.getInstance();
          user=mAuth.getCurrentUser();

        progressDialog=new ProgressDialog(this);

   ;

        //init sippner
        spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dabbaType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(order_daba.this);



        //init sippner
        spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Timing, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);



        //init sippner
        spinner3 = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.Quantity, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(this);

        //init
        name=findViewById(R.id.NameId);
        address=findViewById(R.id.address_id);
        phone=findViewById(R.id.phone_id);
        veg=findViewById(R.id.veg_checkbox);
        nonveg=findViewById(R.id.nonveg_checkbox);


        veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typefood="Veg";
            }
        });
        nonveg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typefood="NonVeg";
            }
        });


        orderFood=findViewById(R.id.orderBtn);
        orderFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString()!=null||address.getText().toString()!=null||phone.getText().toString()!=null||
                        !typefood.equals("")||!dabatype.equals("")||!timing.equals("")||!quantity.equals("")){
                    OrderFoodFromFirebase(name.getText().toString(),address.getText().toString(),phone.getText().toString()
                    ,typefood,dabatype,timing,quantity);
                }
                else {
                    Toast.makeText(order_daba.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void OrderFoodFromFirebase(String name, String address,
                                       String phone, String typefood,
                                       String dabatype, String timing, String quantity) {
    progressDialog.setMessage("Order Sending");
    progressDialog.show();

        String email= user.getEmail();
        String uid=user.getUid();

        //data base
        Query query= FirebaseDatabase.getInstance().getReference("Dabbawala").orderByChild("Email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Dusername =ds.child("Name").getValue().toString();
                    Dlocation=ds.child("location").getValue().toString();
                    Dspecidal=ds.child("special").getValue().toString();
                    Dphone=ds.child("phoneNO").getValue().toString();



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final HashMap<String, String> hashMap=new HashMap<>();
        hashMap.put("User_Name",name);
        hashMap.put("User_Address",address);
        hashMap.put("User_Phone",phone);
        hashMap.put("Food_Type",typefood);
        hashMap.put("Dabba_Size",dabatype);
        hashMap.put("Timing",timing);
        hashMap.put("uid",uid);
        hashMap.put("Quantity",quantity);
        hashMap.put("DabbaWala",dabawlaUid);
        hashMap.put("DabbaWala_Name",Dusername);
        hashMap.put("DabbaWala_Location",Dlocation);
        hashMap.put("DabbaWala_Phone",Dphone);

        ref.child(dabawlaUid).child(uid).setValue(hashMap);
        progressDialog.dismiss();

        //sucess
        Toast.makeText(order_daba.this, "Order Placed"+user.getEmail(), Toast.LENGTH_SHORT).show();

        startActivity(new Intent(order_daba.this, DashBoradActivity.class));
        finish();



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if (text.equals("Small")){
            dabatype=""+text;
        }
         if (text.equals("Medium")){
            dabatype=""+text;
        }
        if (text.equals("Large")){
            dabatype=""+text;
        }
         if(text.equals("10amTo11am")){
            timing=""+text;
        }
         if (text.equals("11amTo12am")){
            timing=""+text;
        }

        if (text.equals("12pmTo1pm")){
            timing=""+text;
        }
        if(text.equals("1")){
            quantity=""+text;
        }
        if(text.equals("2")){
            quantity=""+text;
        }
       if(text.equals("3")){
            quantity=""+text;
        }
         if(text.equals("4")){
            quantity=""+text;
        }
        if(text.equals("5")){
            quantity=""+text;
        }

        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
