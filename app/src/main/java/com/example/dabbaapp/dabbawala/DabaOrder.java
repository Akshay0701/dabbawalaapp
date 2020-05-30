package com.example.dabbaapp.dabbawala;


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
import com.example.dabbaapp.Adapter.AdapterOrder;
import com.example.dabbaapp.Model.ModelDabbawala;
import com.example.dabbaapp.Model.ModelOrder;
import com.example.dabbaapp.R;
import com.example.dabbaapp.user.User_Home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class DabaOrder extends Fragment {

    FirebaseAuth firebaseAuth;

    SharedPreferences.Editor editor;

    AdapterOrder adapterOrder;
    List<ModelOrder> orderList;

    RecyclerView recyclerView;

    String myUid;


    public DabaOrder() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_daba_order, container, false);

        //init
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        firebaseAuth= FirebaseAuth.getInstance();


        orderList=new ArrayList<>();

        checkforuserlogin();
        getAllorder();

        return view;
    }

    private void getAllorder() {
        Query ref= FirebaseDatabase.getInstance().getReference("Order").child(myUid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                   ModelOrder modelOrder=ds.getValue(ModelOrder.class);
                    orderList.add(modelOrder);


                    adapterOrder = new AdapterOrder(getContext(), orderList);

                    recyclerView.setAdapter(adapterOrder);
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
            startActivity(new Intent(getActivity(), User_Home.class));
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
        inflater.inflate(R.menu.dabawala_menu,menu);  super.onCreateOptionsMenu(menu,inflater);
    }

}
