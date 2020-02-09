package com.example.dailyuadb.Controller.Adapter;

import android.content.Context;
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
import com.example.dailyuadb.Model.Post;
import com.example.dailyuadb.Model.User;
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

import de.hdodenhof.circleimageview.CircleImageView;

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, viewGroup,false);
        System.out.println("methode Oncreate");
        return new IdeeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Idee idee= mIdee.get(i);

        /*if (post.getDescription().equals("")){
            viewHolder.description.setVisibility(View.GONE);

        }else {
            viewHolder.description.setVisibility(View.VISIBLE);
            viewHolder.description.setText(post.getDescription());
        }*/

        publicherInfo(viewHolder.image_profil,viewHolder.username,viewHolder.message);
    }


    @Override
    public int getItemCount() {
        return mIdee.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView image_profil,post_image,like,comment;
        public TextView username, message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_profil= itemView.findViewById(R.id.image_profils);
            like= itemView.findViewById(R.id.like);
            comment= itemView.findViewById(R.id.comment);
            username= itemView.findViewById(R.id.username);
            message = itemView.findViewById(R.id.fullname);

        }
    }

    public void publicherInfo(final ImageView image_profile, final TextView username, final TextView description){
        final DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Idees");
            System.out.println("methode publisher");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String idIdee;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()
                     ) {
                    idees = new Idee();
                    idees.setId(snapshot.child("id").getValue().toString());
                    idees.setDescription(snapshot.child("description").getValue().toString());
                    idees.setPrenom(snapshot.child("prenom").getValue().toString());
                    idees.setNom(snapshot.child("nom").getValue().toString());
                    listIdIdee.add(idees);
                }
                for(Idee idea : listIdIdee){
                    Log.i("ideal",idea.getNom() +" "+idea.getPrenom()+ " "+idea.getDescription());
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //Liste des IdUser issue de la table Idees
        for (final Idee listIdees : listIdIdee
             ) {
            reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Idee idee = dataSnapshot.getValue(Idee.class);

                    Glide.with(mContext).load(dataSnapshot.child(listIdees.getId()).child("imageurl").getValue()).into(image_profile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }

    }
}
