package com.example.dailyuadb.Controller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dailyuadb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AjouterActiviteActivity extends AppCompatActivity {

    private EditText id_activite, id_description;
    DatabaseReference reference, referenceActivite;
    private Button button_publier;
    boolean erreur = false;
    FirebaseUser user;
    String uid;

    String prenom = "";
    String nom = "";
    String id = "";
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_activite);

        //Recuperation
        id_activite = findViewById(R.id.id_activite);
        id_description = findViewById(R.id.id_description);

        button_publier = findViewById(R.id.button_publier);
        button_publier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nom_activite = id_activite.getText().toString().trim();
                final String description = id_description.getText().toString().trim();
                if (nom_activite.isEmpty()){
                    erreur = true;
                    id_activite.setError("Le champs nom de l'activité est obligatoire");
                    id_activite.requestFocus();
                    return;
                }

                if (description.isEmpty()){
                    erreur = true;
                    id_description.setError("Le champs description de l'activité est obligatoire");
                    id_description.requestFocus();
                    return;
                }

                if (erreur == false){
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    uid = user.getUid();
                    reference = FirebaseDatabase.getInstance().getReference("Users");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            prenom = (String) dataSnapshot.child(uid).child("prenom").getValue();
                            nom = (String) dataSnapshot.child(uid).child("nom").getValue();
                            id = (String) dataSnapshot.child(uid).child("id").getValue();
                            email = (String) dataSnapshot.child(uid).child("email").getValue();

                            referenceActivite = FirebaseDatabase.getInstance().getReference("Activite");
                            String activiteId = referenceActivite.push().getKey();
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("prenom_publisher", prenom);
                            hashMap.put("nom_publisher", nom);
                            hashMap.put("email_publisher", email);
                            hashMap.put("description", description);
                            hashMap.put("nom_activite", nom_activite);
                            hashMap.put("id_publisher", id);

                            referenceActivite.child(activiteId).setValue(hashMap);

                            Toast.makeText(getApplicationContext(), "Votre activité a été bien ajouté",
                                    Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AjouterActiviteActivity.this, CodifiantActivity.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }


            }
        });
    }
}
