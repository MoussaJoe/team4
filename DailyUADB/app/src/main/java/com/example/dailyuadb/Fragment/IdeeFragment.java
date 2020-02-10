package com.example.dailyuadb.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dailyuadb.Adapter.CommentAdapter;
import com.example.dailyuadb.Controller.Activities.AjoutIdeeActivity;
import com.example.dailyuadb.Controller.Adapter.IdeeAdapter;
import com.example.dailyuadb.Model.Idee;
import com.example.dailyuadb.Model.ui.main.Comment;
import com.example.dailyuadb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class IdeeFragment extends Fragment {

    private View view;
    private ListView mRecyclerView;
    private ImageView mAdd_idea_textView;

    Fragment selectedFragment;
    private RecyclerView recyclerView;
    private IdeeAdapter mIdeeAdapter;
    private List<Idee> ideaList;


    FirebaseUser firebaseUser;

    private DatabaseReference mReference;
    public IdeeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_idee, container, false);
        android.widget.Toolbar toolbar= view.findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);s
       // toolbar.getSupportActionBar().setTitle("Commentaires");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView= view.findViewById(R.id.idea_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ideaList= new ArrayList<>();
        mIdeeAdapter= new IdeeAdapter(this.getContext(),ideaList);
        recyclerView.setAdapter(mIdeeAdapter);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        readComments();

        mAdd_idea_textView = view.findViewById(R.id.add_idea);
        mAdd_idea_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getContext(), AjoutIdeeActivity.class));
            }
        });

        return view;
    }

    private void readComments(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Idees");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ideaList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Idee idee= snapshot.getValue(Idee.class);
                    ideaList.add(idee);
                }

                mIdeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
