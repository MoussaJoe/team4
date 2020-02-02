package com.example.dailyuadb.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dailyuadb.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ListeMenuActivity extends AppCompatActivity {

    private TextView text_jour,text_dejeuner,text_diner;
    private Button btn_note_diner, btn_note_dejeuner;
    private RatingBar ratingBar_diner, ratingBar_dejeuner;
    private float nbre_etoile = 0;

    DatabaseReference reference;
    String date_du_jour = new SimpleDateFormat("EEEE", Locale.FRANCE).format(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_menu);

        text_jour = (TextView) findViewById(R.id.text_jour);
        text_dejeuner = (TextView) findViewById(R.id.text_dejeuner);
        text_diner = (TextView) findViewById(R.id.text_diner);

        btn_note_dejeuner = findViewById(R.id.btn_note_dej);
        btn_note_diner = findViewById(R.id.btn_note_diner);

        ratingBar_dejeuner = findViewById(R.id.ratingBar_dejeuner);
        ratingBar_diner = findViewById(R.id.ratingBar_diner);
        System.out.println("Aujourdhui "+date_du_jour);

        reference = FirebaseDatabase.getInstance().getReference("Menus");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    if (date_du_jour.equalsIgnoreCase(snapshot.child("jour").getValue().toString())){
                        text_dejeuner.setText((String) snapshot.child("dejeuner").getValue());
                        text_diner.setText((String) snapshot.child("diner").getValue());
                        text_jour.setText("Menu du "+(String) snapshot.child("jour").getValue());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        noter_un_repas(ratingBar_dejeuner, btn_note_dejeuner);
        noter_un_repas(ratingBar_diner, btn_note_diner);
    }

    public void noter_un_repas(RatingBar note_du_repas, Button button){
        note_du_repas.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int numChoix = (int) rating;
                String message = "";
                nbre_etoile = ratingBar.getNumStars();

                switch (numChoix){
                    case 1 :
                        message = "Désolé que le repas ne vous ait pas plut";

                    case 2:
                        message = "Nous acceptons vos suggestions";

                    case 3:
                        message = "Bien";

                    case 4:
                        message = "Merci beaucoup";

                    case 5:
                        message = "Encore merci";
                }
                Toast.makeText(ListeMenuActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListeMenuActivity.this, String.valueOf(nbre_etoile), Toast.LENGTH_LONG).show();
            }
        });

    }
}
