package com.example.dailyuadb.Controller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailyuadb.DelegueActivity;
import com.example.dailyuadb.MainActivity;
import com.example.dailyuadb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    DatabaseReference reference;
    String profile;
    FirebaseUser user;
    String uid;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        editTextEmail = findViewById(R.id.auth_activity_email);
        editTextPassword = findViewById(R.id.auth_activity_password);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        findViewById(R.id.auth_activity_connectio_btn).setOnClickListener(this);
        findViewById(R.id.inscription_textView).setOnClickListener(this);
    }

    private void authUser() {

        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.input_error_email));
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.input_error_password));
            editTextPassword.requestFocus();
            return;
        }


        if (password.length() < 4) {
            editTextPassword.setError(getString(R.string.input_error_password_length));
            editTextPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.input_error_email_invalid));
            editTextEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //page d'acceuil place
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    uid = user.getUid();
                    reference = FirebaseDatabase.getInstance().getReference("Users");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            profile = dataSnapshot.child(uid).child("profil").getValue().toString();
                            //search.setText(profile);
                            System.out.println("Mon profile depuis Auth"+profile);
                            if (profile.equalsIgnoreCase("Etudiant")){
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }else if (profile.equalsIgnoreCase("Delegue")){

                                startActivity(new Intent(getApplicationContext(), DelegueActivity.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }

        );

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auth_activity_connectio_btn:
                authUser();
                break;
            case R.id.inscription_textView:
                Intent intent = new Intent(getApplicationContext(), Inscription.class);
                startActivity(intent);
        }
    }
}
