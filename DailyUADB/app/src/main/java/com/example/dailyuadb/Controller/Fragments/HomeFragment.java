package com.example.dailyuadb.Controller.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyuadb.Controller.Adapter.PostAdapter;
import com.example.dailyuadb.Model.Post;
import com.example.dailyuadb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private List<String> post_a_afficher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Depuis FireBase
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Test
       // View view = inflater.inflate(R.layout.post_item, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        postList = new ArrayList();
        postAdapter = new PostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);

        //Pour lire les poste depuis FireBase
        readPost();
        //checkFollowing();
        return view;
    }

    private void checkFollowing(){
        post_a_afficher= new ArrayList<>();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Follow")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Following");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                post_a_afficher.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    post_a_afficher.add(snapshot.getKey());
                }

                //readPost();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void readPost(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        System.out.println("uid "+reference.getKey());
        System.out.println("uid2 "+reference.child("postid"));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    postList.add(post);
                    System.out.println(snapshot.getValue().toString());
                    /*for (String id : post_a_afficher) {
                        if(post.getPublisher().equals(id)){
                            postList.add(post);

                        }

                    }*/
                }

                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
