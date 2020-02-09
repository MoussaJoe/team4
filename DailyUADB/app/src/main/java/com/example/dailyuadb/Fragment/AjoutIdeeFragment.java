package com.example.dailyuadb.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailyuadb.Model.Idee;
import com.example.dailyuadb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AjoutIdeeFragment extends Fragment implements View.OnClickListener{
    ImageView mPhoto_profil;
    EditText mIdee;
    TextView mPost;
    TextView mPrenom_nom;
    ImageView mClose;

    private DatabaseReference mBoiteIdee;
    private DatabaseReference mUsers;
    private FirebaseAuth mAuth;

    String email;

    FirebaseUser user ;
    String uid;
    String prenom;
    String nom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ajout_idee, container, false);

        mPhoto_profil = (ImageView) rootView.findViewById(R.id.image_profils);
        mIdee = (EditText) rootView.findViewById(R.id.idee_discussion);
        mPrenom_nom = (TextView) rootView.findViewById(R.id.prenom_nom);

        mPost = (TextView) rootView.findViewById(R.id.post);
        mClose = (ImageView) rootView.findViewById((R.id.close));

        rootView.findViewById(R.id.post).setOnClickListener(this);


        select_user();




        return rootView;


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post:
                post_idea();
                break;
            case R.id.close:
                Fragment selectedFrangment = new HomeFragment();
                break;
        }
    }

    public void post_idea(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("E dd/MM/yyyy");
        String date_complet = sdf.format(date);
        SimpleDateFormat sdf_hour = new SimpleDateFormat("HH:mm:ss");
        String heure = sdf_hour.format(date);
        Log.i("HEURE", heure);
        Log.i("DATE", date_complet);
        String mIdee_str = mIdee.getText().toString().trim();
        if(!TextUtils.isEmpty(mIdee_str)) {
            mBoiteIdee = FirebaseDatabase.getInstance().getReference("Idees");

            String id = mBoiteIdee.push().getKey();

            Idee idee = new Idee(uid, email, mIdee.getText().toString().trim(), date_complet, heure, nom, prenom);

            mBoiteIdee.child(id).setValue(idee);

            Toast.makeText(getContext(), "idee bien ajoutée", Toast.LENGTH_SHORT).show();
        }
        else {
            mIdee.setError(getString(R.string.input_error_email));
            mIdee.requestFocus();
            return;
        }
    }

    public void select_user(){


        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        mUsers = FirebaseDatabase.getInstance().getReference("Users");
        mUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 email = dataSnapshot.child(uid).child("email").getValue().toString();
                 prenom = dataSnapshot.child(uid).child("prenom").getValue().toString();
                 nom = dataSnapshot.child(uid).child("nom").getValue().toString();
                 mPrenom_nom.setText(prenom+" "+nom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
