package com.example.dailyuadb.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dailyuadb.Controller.Activities.ListeMenuActivity;
import com.example.dailyuadb.Controller.Activities.MenuRestoActivity;
import com.example.dailyuadb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MenuRestoFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private EditText diner_id, dejeuner_id;
    private Spinner id_jour;
    private Button button_add_repas;
    private Button button_lister_menu;
    DatabaseReference referenceMenu;
    DatabaseReference reference;
    DatabaseReference referenceVerifJour;
    private String jour = "" ;
    FirebaseUser user;
    String uid;

    String prenom = "";
    String nom = "";
    String id = "";
    String email = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu_resto, container, false);

        id_jour = view.findViewById(R.id.id_jour);
        diner_id = view.findViewById(R.id.diner_id);
        dejeuner_id = view.findViewById(R.id.dejeuner_id);
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource( getContext(), R.array.liste_jour, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        id_jour.setAdapter(adapter);
        id_jour.setOnItemSelectedListener(this);


        button_add_repas = view.findViewById(R.id.button_add_repas);
        button_add_repas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nom_diner = diner_id.getText().toString().trim();
                final String nom_dejeuner = dejeuner_id.getText().toString().trim();

                //Validation
                if (nom_dejeuner.isEmpty()){
                    dejeuner_id.setError(getString(R.string.input_error_dejeuner));
                    dejeuner_id.requestFocus();
                    return;
                }

                if (nom_diner.isEmpty()){
                    diner_id.setError(getString(R.string.input_error_diner));
                    diner_id.requestFocus();
                    return;
                }

                if (jour.equalsIgnoreCase("Sélectionner un jour")){
                    id_jour.requestFocus();
                    return;
                }

                /*referenceVerifJour = FirebaseDatabase.getInstance().getReference("Menus");
                referenceVerifJour.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()
                        ) {
                            //date = snapshot.getValue(String.class);
                            System.out.println("jour BD "+snapshot.child("jour").getValue()+" et jour "+jour);
                            System.out.println((snapshot.child("jour").getValue().equals(jour)));
                            if ((snapshot.child("jour").getValue().equals(jour))){
                                Toast.makeText(getApplicationContext(), "On a déja un menu pour le "+jour, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MenuRestoActivity.this, MenuRestoActivity.class));
                            }else{
                                System.out.println("Ok on n'est bon");
                            }
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/


                //Récupération des infos du User Connecter
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

                        referenceMenu = FirebaseDatabase.getInstance().getReference("Menus");
                        String menusId = referenceMenu.push().getKey();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("prenom_publisher", prenom);
                        hashMap.put("nom_publisher", nom);
                        hashMap.put("email_publisher", email);
                        hashMap.put("jour", jour);
                        hashMap.put("dejeuner", nom_dejeuner);
                        hashMap.put("diner", nom_diner);
                        hashMap.put("id_publisher", id);

                        //referenceMenu.child(menusId).setValue(hashMap);

                        Toast.makeText(getContext(), "Menu bien ajouté", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(), MenuRestoFragment.class));
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //Evenement Click sur le boutton Lister les menus
        button_lister_menu = view.findViewById(R.id.btn_lister_menu);
        button_lister_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ListeMenuActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        jour = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
