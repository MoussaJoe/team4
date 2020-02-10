package com.example.dailyuadb.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dailyuadb.Controller.Activities.AuthActivity;
import com.example.dailyuadb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    ImageView img;
    TextView nom,prenom,carte,email;
    Button btndisconnect,btnEdit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        img= view.findViewById(R.id.imageProfile);
        nom= view.findViewById(R.id.profilNom);
        prenom= view.findViewById(R.id.profilPrenom);
        carte= view.findViewById(R.id.profilCarte);
        email= view.findViewById(R.id.profilEmail);
        btndisconnect= view.findViewById(R.id.buttonDisconnect);
        btnEdit= view.findViewById(R.id.buttonEditProfil);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth= FirebaseAuth.getInstance();

        String uid= firebaseUser.getUid();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nom.setText(dataSnapshot.child("nom").getValue().toString());
                prenom.setText(dataSnapshot.child("prenom").getValue().toString());
                carte.setText(dataSnapshot.child("numCarte").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
           //     Glide.with(mContext).load(dataSnapshot.child("imageurl").getValue()).into(img);
                Picasso.get().load(dataSnapshot.child("imageurl").getValue().toString()).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btndisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                startActivity(new Intent(getContext(),AuthActivity.class));

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }


}

