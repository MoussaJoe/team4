package com.example.dailyuadb.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyuadb.Controller.Activities.AcceuilActivity;
import com.example.dailyuadb.DelegueActivity;
import com.example.dailyuadb.MainActivity;
import com.example.dailyuadb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
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
import java.util.HashMap;
import java.util.Locale;

public class ListeMenuActivity extends AppCompatActivity {

    private TextView text_jour,text_dejeuner,text_diner;
    private Button btn_note_diner, btn_note_dejeuner;
    private RatingBar ratingBar_diner, ratingBar_dejeuner;
    private float nbre_etoile = 0;
    DatabaseReference reference, referenceNoteMenu;

    DatabaseReference referenceVerifJour;
    FirebaseUser user;
    String uid;
    String prenom = "";
    String nom = "";
    String id = "";
    String email = "";
    String nom_menu_dejeuner, nom_menu_diner, jour = "";

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
                        nom_menu_dejeuner = (String) snapshot.child("dejeuner").getValue();
                        nom_menu_diner = (String) snapshot.child("diner").getValue();
                        jour = (String) snapshot.child("jour").getValue();
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

        noter_un_repas(ratingBar_dejeuner, btn_note_dejeuner, "Dejeuner");
        noter_un_repas(ratingBar_diner, btn_note_diner, "Diner");
    }

    public void noter_un_repas(RatingBar note_du_repas, Button button, final String typeRepas){
        note_du_repas.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int numChoix = (int) rating;
                String message = "";
                nbre_etoile = ratingBar.getRating()   ;

                switch (numChoix){
                    case 1 :
                        message = "Désolé que le repas ne vous ait pas plu";

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

                        referenceNoteMenu = FirebaseDatabase.getInstance().getReference("NoteMenus");
                        String noteMenusId = referenceNoteMenu.push().getKey();
                        HashMap<String, Object> hashMap = new HashMap<>();

                        hashMap.put("prenom_publisher", prenom);
                        hashMap.put("nom_publisher", nom);
                        hashMap.put("email_publisher", email);
                        hashMap.put("jour", jour);
                        if (typeRepas.equalsIgnoreCase("Dejeuner")){
                            hashMap.put("dejeuner", nom_menu_dejeuner);
                        }else if (typeRepas.equalsIgnoreCase("Diner")){
                            hashMap.put("diner", nom_menu_diner);
                        }
                        hashMap.put("id_publisher", id);
                        hashMap.put("note_menu", nbre_etoile);

                        referenceNoteMenu.child(noteMenusId).setValue(hashMap);
                        if (dataSnapshot.child(uid).child("profil").getValue().toString().equalsIgnoreCase("Etudiant")){
                            startActivity(new Intent(getApplicationContext(), AcceuilActivity.class));
                            Toast.makeText(ListeMenuActivity.this, "Merci d'avoir noter le repas!! A bientot", Toast.LENGTH_LONG).show();
                        }else if (dataSnapshot.child(uid).child("profil").getValue().toString().equalsIgnoreCase("Delegue")){
                            startActivity(new Intent(getApplicationContext(), DelegueActivity.class));
                            Toast.makeText(ListeMenuActivity.this, "Merci d'avoir noter le repas!! A bientot", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
