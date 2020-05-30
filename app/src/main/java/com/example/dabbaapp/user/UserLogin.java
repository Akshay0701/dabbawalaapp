package com.example.dabbaapp.user;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.dabbaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;

    ProgressDialog dialog;
    EditText userName,emailEt,phoneNo,passwordEt;
    Button registerBtn;
    TextView alreadyLogin;

    FirebaseDatabase database ;
    DatabaseReference myRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        alreadyLogin=findViewById(R.id.already_register);




        userName=findViewById(R.id.NameEt);
        emailEt=findViewById(R.id.emailEt);
        phoneNo=findViewById(R.id.phoneNoEt);
        passwordEt=findViewById(R.id.passwordEt);
        registerBtn=findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password,phoneNoo,Name;
                email=emailEt.getText().toString();
                password=passwordEt.getText().toString();
                phoneNoo=phoneNo.getText().toString();
                Name=userName.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    //set
                    emailEt.setError("Ivaild Email");
                    emailEt.setFocusable(true);
                }
                else if(password.length()<6) {
                    passwordEt.setError("Ivaild password");
                    passwordEt.setFocusable(true);
                }
                else {
                    SharedPreferences.Editor editor;
                    editor= PreferenceManager.getDefaultSharedPreferences(UserLogin.this).edit();
                    editor.putString("username", email.trim());
                    editor.putString("password", password.trim());
                    editor.apply();
                    registerNewUser(email, password, Name, phoneNoo);
                }
                }
        });
        alreadyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserLogin.this, UserSignIn.class);
                startActivity(intent);
            }
        });
    }

    private void registerNewUser(String email, final String password, final String name, final String phoneNoo) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dialog.setMessage("registering..");
        dialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            String email= user.getEmail();
                            String uid=user.getUid();
                            HashMap<Object,String> hashMap=new HashMap<>();

                            hashMap.put("uId",uid);
                            hashMap.put("Name",name);
                            hashMap.put("Email",email);
                            hashMap.put("Phone",phoneNoo);
                            hashMap.put("Password",password);

                            FirebaseDatabase database=FirebaseDatabase.getInstance();

                            DatabaseReference reference=database.getReference("Users");
                            reference.child(uid).setValue(hashMap);
                            //sucess
                            Toast.makeText(UserLogin.this, "Registered with "+user.getEmail(), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(UserLogin.this, DashBoradActivity.class));
                            finish();

                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserLogin.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final ProgressDialog progressDialog=new ProgressDialog(this);

        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        String username=prefs.getString("username","");
        String pass=prefs.getString("password","");

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
                                startActivity(new Intent(UserLogin.this, DashBoradActivity.class));
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                progressDialog.dismiss();
                                Toast.makeText(UserLogin.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UserLogin.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        super.onStart();
    }
}
