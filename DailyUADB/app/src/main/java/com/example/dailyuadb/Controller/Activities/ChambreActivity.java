package com.example.dailyuadb.Controller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dailyuadb.R;

public class ChambreActivity extends AppCompatActivity {

    private Button btn_add_chambre, btn_lister_chambre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chambre);

        //Redirection vers l'activité précédent
        getSupportActionBar().setTitle("Chambre");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Redirection vers Ajouter Chambre
        btn_add_chambre = findViewById(R.id.btn_add_chambre);
        btn_add_chambre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChambreActivity.this, AjouterChambreActivity.class));
            }
        });

        //Redirection vers lister Chambres

        btn_lister_chambre = findViewById(R.id.btn_lister_chambre);
        btn_lister_chambre.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChambreActivity.this, ChambreActivity.class));
            }
        });
    }
}
