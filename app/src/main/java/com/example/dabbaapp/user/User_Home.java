package com.example.dabbaapp.user;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dabbaapp.Adapter.AdapterDabawala;
import com.example.dabbaapp.Model.ModelDabbawala;
import com.example.dabbaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class User_Home extends Fragment {

    FirebaseAuth firebaseAuth;

    SharedPreferences.Editor editor;

    AdapterDabawala adapterDabawala;
    List<ModelDabbawala> userList;

    RecyclerView recyclerView;

    String myUid;


    public User_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user__home, container, false);

        //init
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        firebaseAuth= FirebaseAuth.getInstance();


        userList=new ArrayList<>();


        getallDabbaWala();
    return view;
    }

    private void getallDabbaWala() {
        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Dabbawala");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    ModelDabbawala modelUser = ds.getValue(ModelDabbawala.class);
                        userList.add(modelUser);


                    adapterDabawala = new AdapterDabawala(getActivity(), userList);

                    recyclerView.setAdapter(adapterDabawala);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void checkforuserlogin() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {

            myUid=user.getUid();

        }
        else{
            startActivity(new Intent(getActivity(),User_Home.class));
            try {
                Objects.requireNonNull(getActivity()).finish();
            }catch (NullPointerException e){

            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.user_menu_nav,menu);  super.onCreateOptionsMenu(menu,inflater);
    }

}
