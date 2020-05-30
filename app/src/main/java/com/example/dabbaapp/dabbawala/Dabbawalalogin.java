package com.example.dabbaapp.dabbawala;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dabbaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Dabbawalalogin extends AppCompatActivity {
    private FirebaseAuth mAuth;

    ProgressDialog dialog;
    EditText UsernameEd,passwordEd;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dabbawalalogin);

        UsernameEd=findViewById(R.id.UsernameEd);
        passwordEd=findViewById(R.id.passwordEd);
        mAuth= FirebaseAuth.getInstance();

        dialog=new ProgressDialog(this);
        loginBtn=findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username,password;
                username=UsernameEd.getText().toString();
                password=passwordEd.getText().toString();
                if(username.equals("")) {
                    //set

                    UsernameEd.setError("Ivaild Username");
                    UsernameEd.setFocusable(true);
                }
                else if(password.length()<6) {
                    passwordEd.setError("Ivaild password");
                    passwordEd.setFocusable(true);
                }
                else {
                    SharedPreferences.Editor editor;
                    editor= PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
                    editor.putString("daba_username", username.trim());
                    editor.putString("daba_password", password.trim());
                    editor.apply();
                    loginDabavala(username, password);
                }
            }
        });
    }

    private void loginDabavala(final String username, final String password) {
        final ProgressDialog  progressDialog;
        progressDialog=new ProgressDialog(this,R.style.Theme_AppCompat_DayNight_DarkActionBar);
        progressDialog.setMessage("Logging...");
        progressDialog.show();


        final FirebaseAuth mAuth;
        mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(Dabbawalalogin.this, DabbawalaDashboard.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(Dabbawalalogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Dabbawalalogin.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
