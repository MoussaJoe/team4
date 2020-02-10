package com.example.dailyuadb.Controller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dailyuadb.Controller.Activities.CommentsActivity;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    public Context mContext;
    public List<Post> mPost;
    public Post post;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, viewGroup,false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Post post= mPost.get(i);

        Glide.with(mContext).load(post.getPostImage()).into(viewHolder.post_image);

        if (post.getDescription().equals("")){
            viewHolder.description.setVisibility(View.GONE);

        }else {
            viewHolder.description.setVisibility(View.VISIBLE);
            viewHolder.description.setText(post.getDescription());
        }

        publisherInfo(viewHolder.image_profil,viewHolder.username);

        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, CommentsActivity.class);
                intent.putExtra("postid",post.getPostId());
                intent.putExtra("publisherid",post.getPublisher());
                mContext.startActivity(intent);
            }
        });

        viewHolder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, CommentsActivity.class);
                intent.putExtra("postid",post.getPostId());
                intent.putExtra("publisherid",post.getPublisher());
                mContext.startActivity(intent);
            }
        });

        getComments(post.getPostId(), viewHolder.comments);

    }



    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profil,post_image,like,comment;
        public TextView username,likes,publicher,description,comments;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_profil= itemView.findViewById(R.id.image_profils);
            post_image= itemView.findViewById(R.id.post_image);
            like= itemView.findViewById(R.id.like);
            comment= itemView.findViewById(R.id.comment);
            username= itemView.findViewById(R.id.username);
            likes= itemView.findViewById(R.id.likes);
            publicher= itemView.findViewById(R.id.publisher);
            description= itemView.findViewById(R.id.description);
            comments= itemView.findViewById(R.id.comments);
        }
    }

    private void getComments(String postid, final TextView comments){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Comments");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.setText("Voir les "+dataSnapshot.getChildrenCount()+" commentaires");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void publisherInfo(final ImageView image_profile, final TextView username){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
      //  DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("Posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot s: dataSnapshot.getChildren()
                     ) {
                    Glide.with(mContext).load(s.child("imageurl").getValue()).into(image_profile);
                    username.setText(s.child("prenom").getValue().toString()+" "+s.child("nom").getValue().toString());
                   // System.out.println("img: "+s.child("imageurl"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       /* reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s: dataSnapshot.getChildren()
                ) {
                    System.out.println("Img "+s.child("postimage").getValue());
                    Glide.with(mContext).load(s.child("postimage").getValue()).into(post_image);
                   // publicher.setText(s.child("publisher").getValue().toString());
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        }); */

    }


}
