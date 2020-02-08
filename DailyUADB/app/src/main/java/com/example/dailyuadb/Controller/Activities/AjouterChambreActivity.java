 package com.example.dailyuadb.Controller.Activities;

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

 public class AjouterChambreActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
     private Spinner id_campus, id_pavillon;
     private EditText num_chambre;
     private Button button_add_chambre, btn_lister_chambre;
     private TextView textView_pavillon;
     private String nom_pavillon = "";
     private String nom_campus = "";
     private int numero_chambre = 0;
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
                    return;
                }

                if (nom_campus.equalsIgnoreCase("Campus 1") && (nom_pavillon.equalsIgnoreCase("Pavillon C") ||
                        nom_pavillon.equalsIgnoreCase("Pavillon D"))){
                    Toast.makeText(getApplicationContext(), "Il n'y a pas de "+nom_pavillon+" au "+nom_campus,
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AjouterChambreActivity.this, AjouterChambreActivity.class));
                }

                if (nom_campus.equalsIgnoreCase("Campus 2") && (nom_pavillon.equalsIgnoreCase("Pavillon A") ||
                        nom_pavillon.equalsIgnoreCase("Pavillon B"))){
                    Toast.makeText(getApplicationContext(), "Il n'y a pas de "+nom_pavillon+" au "+nom_campus,
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AjouterChambreActivity.this, AjouterChambreActivity.class));

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
