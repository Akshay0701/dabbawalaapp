package com.example.dabbaapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dabbaapp.dabbawala.DabaOrderActivity;
import com.example.dabbaapp.Model.ModelDabbawala;
import com.example.dabbaapp.R;

import java.util.List;

public class AdapterDabawala extends RecyclerView.Adapter<AdapterDabawala.MyHolder> {

    Context context;
    List<ModelDabbawala> userList;


    public AdapterDabawala(Context context, List<ModelDabbawala> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_dabawala,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {


        final String hisId=userList.get(position).getUid();
        String dabaWalaName=userList.get(position).getName();
        String dabaWalaLocation=userList.get(position).getLocation();
        String dabawalaSpecial=userList.get(position).getSpecial();


        //setdata
        holder.mNameTv.setText(dabaWalaName);
        // Toast.makeText(context, ""+hisUID, Toast.LENGTH_SHORT).show();
        holder.mlocationTv.setText(dabaWalaLocation);
        holder.mspecialTv.setText(dabawalaSpecial);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DabaOrderActivity.class);
                intent.putExtra("hisId", hisId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {


        TextView mNameTv, mlocationTv, mspecialTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mNameTv=itemView.findViewById(R.id.nametv);
            mlocationTv=itemView.findViewById(R.id.Dblocation);
            mspecialTv=itemView.findViewById(R.id.Dbspecial);

        }


    }
}
