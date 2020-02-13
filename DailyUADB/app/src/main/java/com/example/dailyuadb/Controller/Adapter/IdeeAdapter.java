package com.example.dailyuadb.Controller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dailyuadb.Model.Idee;
import com.example.dailyuadb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class IdeeAdapter extends RecyclerView.Adapter<IdeeAdapter.ViewHolder> {

    public Context mContext;
    public List<Idee> mIdee;
    List<Idee> listIdIdee = new ArrayList<>();
    Idee idees ;

    private FirebaseUser firebaseUser;
    public IdeeAdapter(Context mContext, List<Idee> mIdee) {
        this.mContext = mContext;
        this.mIdee = mIdee;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.idee_item, viewGroup,false);
        System.out.println("methode Oncreate");
        return new IdeeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final Idee idees= mIdee.get(i);
        viewHolder.idee.setText(idees.getDescription());
        viewHolder.dateTime.setText(idees.getDate()+" à "+idees.getHeure());
        getUserInfo(viewHolder.image_profile,viewHolder.username, idees.getId());

    }


    @Override
    public int getItemCount() {
        return mIdee.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public ImageView image_profile;
        public TextView username, idee, dateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_profile= itemView.findViewById(R.id.image_profile);
            username= itemView.findViewById(R.id.username);
            idee= itemView.findViewById(R.id.comment);
            dateTime = itemView.findViewById(R.id.datetime);
        }
    }

    private void getUserInfo(final ImageView imageView, final TextView username, String id){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Glide.with(mContext).load(dataSnapshot.child("imageurl").getValue()).into(imageView);
                username.setText(dataSnapshot.child("prenom").getValue().toString()+" "+dataSnapshot.child("nom").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
