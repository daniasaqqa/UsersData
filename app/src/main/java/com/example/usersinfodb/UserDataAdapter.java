package com.example.usersinfodb;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.MyViewHolder> {
    public UserDataAdapter(ArrayList<UserDataModel> data, Context ctx) {
        this.data = data;
        this.ctx = ctx;
    }

    ;
    ArrayList<UserDataModel> data;

    Context ctx;
    FirebaseFirestore fb = FirebaseFirestore.getInstance();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemIn = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_data_rc, parent, false);
        return new MyViewHolder(itemIn);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvName.setText(data.get(position).getUserNameM());
        holder.tvMobile.setText(data.get(position).getMobileNumberM());
        holder.tvAddress.setText(data.get(position).getAddressM());
        holder.btnDeletee.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                fb.collection("Users").document(data.get(position).getId()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                data.remove(position);
                                MainActivity.getDataU();


                                Toast.makeText(ctx, "Success", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ctx, "Failer delete", Toast.LENGTH_LONG).show();
                            }
                        });

            }

        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvMobile;
        public TextView tvAddress;
        public Button btnDeletee;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_username);
            tvMobile = itemView.findViewById(R.id.tv_number);
            tvAddress = itemView.findViewById(R.id.tv_address);
            btnDeletee = itemView.findViewById(R.id.btn_delete);
        }
    }

}
