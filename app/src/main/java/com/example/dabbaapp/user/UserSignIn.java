package com.example.dabbaapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;


    EditText emailEt,passwordEt;
    Button loginBtn;

    FirebaseDatabase database ;
    DatabaseReference myRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);
        emailEt=findViewById(R.id.emailEt);
        passwordEt=findViewById(R.id.passwordEt);
        mAuth=FirebaseAuth.getInstance();


        loginBtn=findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email=emailEt.getText().toString();
                password=passwordEt.getText().toString();
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
                    editor= PreferenceManager.getDefaultSharedPreferences(UserSignIn.this).edit();
                    editor.putString("username", email.trim());
                    editor.putString("password", password.trim());
                    editor.apply();
                    LoginUser(email, password);
                }
            }
        });

    }

    private void LoginUser(String email, String password) {


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in Log.d(TAG, "signInWithEmail:success");
                          //  FirebaseUser user = mAuth.getCurrentUser();
                            //start new Acoount

                            startActivity(new Intent(UserSignIn.this, DashBoradActivity.class));

                        } else {

                            // If sign in fails, display a message to the user.Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(UserSignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });



    }
}
