package com.example.dabbaapp.dabbawala;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dabbaapp.R;
import com.example.dabbaapp.user.DashBoradActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Dabawalaregister extends AppCompatActivity {


    EditText daba_name,daba_email,daba_password,daba_PhoneNo,daba_Address,daba_Special;


    //process dialog
    ProgressDialog progressDialog;


    private FirebaseAuth mAuth;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Dabbawala");



    Button submit,singIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dabawalaregister);

        //init firebase
        mAuth = FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this,4);
        //init edit text
        daba_name=findViewById(R.id.daba_name);
        daba_email=findViewById(R.id.daba_email);
        daba_password=findViewById(R.id.daba_password);
        daba_PhoneNo=findViewById(R.id.daba_PhoneNo);
        daba_Address=findViewById(R.id.daba_Address);
        daba_Special=findViewById(R.id.daba_Special);

        submit=findViewById(R.id.daba_submit);
        singIn=findViewById(R.id.daba_signIn);
        progressDialog.setMessage("Registering user...");



        //handle btn if user is already register and he/she want to just sign in
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dabawalaregister.this,Dabbawalalogin.class));
            }
        });

        //noinspection deprecation
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(User_Register.this, "submit", Toast.LENGTH_SHORT).show();

                //user info
                String name,email,address,phone,password,special;
                //user donated
                String donated ="";
                //init
                email= daba_email.getText().toString();
                name= daba_name.getText().toString();
                address= daba_Address.getText().toString();
                phone= daba_PhoneNo.getText().toString();
                password=daba_password.getText().toString();
                special=daba_Special.getText().toString();


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    daba_email.setError("Invalided Email");
                    daba_email.setFocusable(true);

                }
                else if(password.length()<6){
                    daba_password.setError("Password length at least 6 characters");
                    daba_password.setFocusable(true);
                }
                else {
                    SharedPreferences.Editor editor;
                    editor= PreferenceManager.getDefaultSharedPreferences(Dabawalaregister.this).edit();
                    editor.putString("daba_username", email.trim());
                    editor.putString("daba_password", password.trim());
                    editor.apply();

                    registerUser(name,email,address,phone,password,special);

                }

            }
        });


    }

    private void registerUser(final String name, String email, final String address, final String phone, final String password, final String special) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {



                            progressDialog.dismiss();
                            FirebaseUser dabawala = mAuth.getCurrentUser();

                            String email= dabawala.getEmail();
                            String uid=dabawala.getUid();
                            final HashMap<Object,String> hashMap=new HashMap<>();

                            //check if commander is allocated or  not
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Dabbawala");
                            hashMap.put("location",address);
                            hashMap.put("Password",password);
                            hashMap.put("PhoneNO",phone);
                            hashMap.put("special",special);
                            hashMap.put("uid",uid);
                            hashMap.put("Name",name);
                            hashMap.put("Email",email);




                            ref.child(uid).setValue(hashMap);

                            //sucess
                            Toast.makeText(Dabawalaregister.this, "Registered with "+dabawala.getEmail(), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(Dabawalaregister.this, DabbawalaDashboard.class));
                            finish();


                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(Dabawalaregister.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Dabawalaregister.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    protected void onStart()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final ProgressDialog progressDialog=new ProgressDialog(this);

        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        String username=prefs.getString("daba_username","");
        String pass=prefs.getString("daba_password","");

        if(username.equals("")&&pass.equals("")) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setMessage("Logging...");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(username, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(Dabawalaregister.this,DabbawalaDashboard.class));
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                progressDialog.dismiss();
                                Toast.makeText(Dabawalaregister.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Dabawalaregister.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        super.onStart();
    }

}
