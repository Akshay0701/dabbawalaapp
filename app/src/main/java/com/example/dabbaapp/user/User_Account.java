package com.example.dabbaapp.user;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dabbaapp.MainActivity;
import com.example.dabbaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class User_Account extends Fragment {



    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    String uid;

    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("User");
    //all acitvity component
    TextView userName,userEmail,userPass,userPhone,UIDtxt;

    TextView scanQr;
    //logout
    Button logoutBtn;

    public User_Account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user__account, container, false);
        //init firebase
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");


        userName=view.findViewById(R.id.Name);
        logoutBtn=view.findViewById(R.id.logout);
        userEmail=view.findViewById(R.id.Email);
        userPass=view.findViewById(R.id.passord);
        userPhone=view.findViewById(R.id.Phone);
        UIDtxt=view.findViewById(R.id.uId);

        //handle logout
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor;
                editor= PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                editor.remove("username");
                editor.remove("password");
                editor.apply();
                firebaseAuth.signOut();
                //checkforuserlogin();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        Query query=databaseReference.orderByChild("Email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){


                    String name,uid1,email,pass,phone;
                     name=""+ds.child("Name").getValue();
                    uid1=""+ds.child("uId").getValue();
                    email=""+ds.child("Email").getValue();
                    pass=""+ds.child("Password").getValue();
                    phone=""+ds.child("Phone").getValue();


                    userName.setText(name);
                    userEmail.setText(email);
                    UIDtxt.setText(uid1);
                    userPass.setText(pass);
                    userPhone.setText(phone);





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


        return view;
    }

}
