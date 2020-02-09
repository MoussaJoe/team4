package com.example.dailyuadb.Fragment;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.dailyuadb.Controller.Adapter.IdeeAdapter;
import com.example.dailyuadb.Model.Idee;
import com.example.dailyuadb.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class IdeeFragment extends Fragment {

    private View view;
    private ListView mRecyclerView;
    private ArrayAdapter<String> arrayAdapter;
    private IdeeAdapter mIdeeAdapter;
    private ArrayList<String> ideaList = new ArrayList<>();
    private TextView mTextView;

    private DatabaseReference mReference;
    public IdeeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_idee, container, false);
        mReference = FirebaseDatabase.getInstance().getReference().child("Idees");
        initializeFields();
        retreiveAndDisplayIdeas();
        return view;
    }

    private void retreiveAndDisplayIdeas() {

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> idees = new ArrayList<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    String desc = ((DataSnapshot) iterator.next()).child("description").getValue().toString();
                    System.out.println(desc);
                   idees.add(desc);
                }
                ideaList.clear();
                ideaList.addAll(idees);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initializeFields() {
        mRecyclerView = (ListView) view.findViewById(R.id.idea_recyclerView);
        arrayAdapter =  new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, ideaList);
    }


}
