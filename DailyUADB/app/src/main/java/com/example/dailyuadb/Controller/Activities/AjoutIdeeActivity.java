package com.example.dailyuadb.Controller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailyuadb.Fragment.HomeFragment;
import com.example.dailyuadb.Fragment.IdeeFragment;
import com.example.dailyuadb.Fragment.ProfileFragment;
import com.example.dailyuadb.Model.Idee;
import com.example.dailyuadb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AjoutIdeeActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mPhoto_profil;
    EditText mIdee;
    Button mPost;
    TextView mPrenom_nom;

    Fragment selectedFrangment;

    private DatabaseReference mBoiteIdee;
    private DatabaseReference mUsers;
    private FirebaseAuth mAuth;

    String email;

    FirebaseUser user ;
    String uid;
    String prenom;
    String nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_idee);

        android.widget.Toolbar toolbar= findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);s
        getSupportActionBar().setTitle("Ajouter Idée");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPhoto_profil = (ImageView) findViewById(R.id.image_profils);
        mIdee = (EditText) findViewById(R.id.idee_discussion);
        mPrenom_nom = (TextView) findViewById(R.id.prenom_nom);

        mPost = (Button) findViewById(R.id.post);

        findViewById(R.id.post).setOnClickListener(this);


        select_user();
        getImage();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post:
                post_idea();
                selectedFrangment = new IdeeFragment();
                break;

        }
    }

    public void post_idea(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("E dd/MM/yyyy");
        String date_complet = sdf.format(date);
        SimpleDateFormat sdf_hour = new SimpleDateFormat("HH:mm");
        String heure = sdf_hour.format(date);
        Log.i("HEURE", heure);
        Log.i("DATE", date_complet);
        String mIdee_str = mIdee.getText().toString().trim();
        if(!TextUtils.isEmpty(mIdee_str)) {
            mBoiteIdee = FirebaseDatabase.getInstance().getReference("Idees");

            String id = mBoiteIdee.push().getKey();

            Idee idee = new Idee(uid, email, mIdee.getText().toString().trim(), date_complet, heure, nom, prenom);

            mBoiteIdee.child(id).setValue(idee);

            Toast.makeText(getApplicationContext(), "idee bien ajoutée", Toast.LENGTH_SHORT).show();
            mIdee.setText("");
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

    private void getImage(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(uid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.child("imageurl").getValue().toString()).into(mPhoto_profil);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
