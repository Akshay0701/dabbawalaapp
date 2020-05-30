package com.example.dabbaapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dabbaapp.Model.ModelDabbawala;
import com.example.dabbaapp.Model.ModelOrder;
import com.example.dabbaapp.R;
import com.example.dabbaapp.dabbawala.DabaOrderActivity;
import com.example.dabbaapp.dabbawala.DabaOrderDetails;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.MyHolder>{

    Context context;
    List<ModelOrder> orderList;

    public AdapterOrder(Context context, List<ModelOrder> orderListr) {
        this.context = context;
        this.orderList = orderListr;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_getorder,parent,false);
        return new AdapterOrder.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        final String userName=orderList.get(position).getUser_Name();
        final String uid=orderList.get(position).getUid();
        String userAddress=orderList.get(position).getUser_Address();
        String userQuantity=orderList.get(position).getQuantity();
        final String dabbaWalaId=orderList.get(position).getDabbaWala();


        //setdata
        holder.mNameTv.setText(userName);
        // Toast.makeText(context, ""+hisUID, Toast.LENGTH_SHORT).show();
        holder.mlocationTv.setText(userAddress);
        holder.mQuantity.setText(userQuantity);


        //handle on click it will redirect to acivity that show user details with order and timing

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DabaOrderDetails.class);
                intent.putExtra("dabawalaId", dabbaWalaId);
                intent.putExtra("uid", uid);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {


        TextView mNameTv, mlocationTv, mQuantity;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mNameTv=itemView.findViewById(R.id.Unametv);
            mlocationTv=itemView.findViewById(R.id.Ulocation);
            mQuantity=itemView.findViewById(R.id.UQuantity);



        }
    }
}
