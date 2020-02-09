package com.example.dailyuadb.Controller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dailyuadb.R;

public class Activites_Chambre extends AppCompatActivity {
    private Button btn_add_chambre, btn_lister_chambre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activites__chambre);

        //Redirection vers Ajouter Chambre
        btn_add_chambre = findViewById(R.id.btn_add_chambre);
        btn_add_chambre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activites_Chambre.this, AjouterChambreActivity.class));
            }
        });

        //Redirection vers lister Chambres

        btn_lister_chambre = findViewById(R.id.btn_lister_chambre);
        btn_lister_chambre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activites_Chambre.this, Activites_Chambre.class));
            }
        });
    }
}
