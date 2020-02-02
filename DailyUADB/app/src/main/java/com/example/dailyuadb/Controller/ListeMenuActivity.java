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
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ListeMenuActivity extends AppCompatActivity {

    private TextView text_jour,text_dejeuner,text_diner;
    DatabaseReference reference;
    String date_du_jour = new SimpleDateFormat("EEEE", Locale.FRANCE).format(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_menu);

        text_jour = (TextView) findViewById(R.id.text_jour);
        text_dejeuner = (TextView) findViewById(R.id.text_dejeuner);
        text_diner = (TextView) findViewById(R.id.text_diner);
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

    }
}
