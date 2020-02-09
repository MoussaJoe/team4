 package com.example.dailyuadb.Controller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailyuadb.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

 public class AjouterChambreActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
     private Spinner id_campus, id_pavillon;
     private EditText num_chambre;
     private Button button_add_chambre, btn_lister_chambre;
     private TextView textView_pavillon;
     private String nom_pavillon = "";
     private String nom_campus = "";
     private int numero_chambre = 0;
     FirebaseUser user;
     String uid;
     DatabaseReference reference, referenceChambre;

     String prenom = "";
     String nom = "";
     String id = "";
     String email = "";
     boolean erreur = false;
     ArrayAdapter<CharSequence> adapter;
     ArrayAdapter<CharSequence> adapterPav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_chambre);


        id_campus = findViewById(R.id.id_campus);
        id_pavillon = findViewById(R.id.id_pavillon);
        textView_pavillon = findViewById(R.id.textView_pavillon);
        num_chambre = findViewById(R.id.num_chambre);

        //Afficher la lister du Spinner campus

        adapter = ArrayAdapter.createFromResource( this, R.array.liste_campus, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        id_campus.setAdapter(adapter);
        id_campus.setOnItemSelectedListener(this);


        adapterPav = ArrayAdapter.createFromResource( this, R.array.liste_pavillon, android.R.layout.simple_spinner_item);
        adapterPav.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        id_pavillon.setAdapter(adapterPav);
        id_pavillon.setOnItemSelectedListener(this);

        //Recuper les bouttons
        button_add_chambre = findViewById(R.id.button_add_chambre);
        button_add_chambre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numero_chambre = Integer.parseInt(0 + num_chambre.getText().toString().trim());

                //Controle de saisi

                if (numero_chambre <= 0){
                    num_chambre.setError("Le numéro de chambre ne peut être vide, null ou négatif");
                    num_chambre.requestFocus();
                    erreur = true;
                    return;
                }

                if (nom_campus.equalsIgnoreCase("Campus 1") && (nom_pavillon.equalsIgnoreCase("Pavillon C") ||
                        nom_pavillon.equalsIgnoreCase("Pavillon D"))){
                    erreur = true;
                    Toast.makeText(getApplicationContext(), "Il n'y a pas de "+nom_pavillon+" au "+nom_campus,
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AjouterChambreActivity.this, AjouterChambreActivity.class));
                }

                if (nom_campus.equalsIgnoreCase("Campus 2") && (nom_pavillon.equalsIgnoreCase("Pavillon A") ||
                        nom_pavillon.equalsIgnoreCase("Pavillon B"))){
                    erreur = true;
                    Toast.makeText(getApplicationContext(), "Il n'y a pas de "+nom_pavillon+" au "+nom_campus,
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AjouterChambreActivity.this, AjouterChambreActivity.class));

                }
                System.out.println("Numéro chambre "+numero_chambre);

                if(nom_campus.equalsIgnoreCase("Campus 1") && (numero_chambre >= 50)){
                    erreur = true;
                    Toast.makeText(getApplicationContext(), "Il n'y a pas de chambre "+numero_chambre+" au "+nom_campus,
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AjouterChambreActivity.this, AjouterChambreActivity.class));
                }

                if(nom_campus.equalsIgnoreCase("Campus 2") && (numero_chambre >= 53)){
                    erreur = true;
                    Toast.makeText(getApplicationContext(), "Il n'y a pas de chambre "+numero_chambre+" au "+nom_campus,
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AjouterChambreActivity.this, AjouterChambreActivity.class));
                }

                //Récupération des infos du User Connecter
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

                        referenceChambre = FirebaseDatabase.getInstance().getReference("Chambre");
                        String chambreId = referenceChambre.push().getKey();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("prenom_publisher", prenom);
                        hashMap.put("nom_publisher", nom);
                        hashMap.put("email_publisher", email);
                        hashMap.put("campus", nom_campus);
                        hashMap.put("pavillon", nom_pavillon);
                        hashMap.put("numero_chambre", numero_chambre);
                        hashMap.put("id_publisher", id);

                        referenceChambre.child(chambreId).setValue(hashMap);

                        Toast.makeText(getApplicationContext(), "La chambre a été bien ajouté", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AjouterChambreActivity.this, AjouterChambreActivity.class));
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                }
            }
        });
    }


     @Override
     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.id_campus){
            nom_campus = parent.getItemAtPosition(position).toString();
        }
        if (parent.getId() == R.id.id_pavillon){
            nom_pavillon = parent.getItemAtPosition(position).toString();
        }

     }

     @Override
     public void onNothingSelected(AdapterView<?> parent) {

     }
 }
