package com.example.dailyuadb.Controller.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.dailyuadb.Fragment.HomeFragment;
import com.example.dailyuadb.R;
import com.example.dailyuadb.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Inscription extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextNom, editTextPrenom, editTextCarte, editTextEmail, editTextPassword, editTextConfirm;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        editTextNom = findViewById(R.id.nom);
        editTextPrenom = findViewById(R.id.prenom);
        editTextCarte = findViewById(R.id.carte);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirm = findViewById(R.id.confirm);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.inscription_valide_btn).setOnClickListener(this);

        getSupportActionBar().setTitle("Inscription");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void registerUser() {
        final String nom = editTextNom.getText().toString().trim();
        final String prenom = editTextPrenom.getText().toString().trim();
        final String carte = editTextCarte.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String confirm = editTextConfirm.getText().toString().trim();

        if (nom.isEmpty()) {
            editTextNom.setError(getString(R.string.input_error_nom));
            editTextNom.requestFocus();
            return;
        }
        if (prenom.isEmpty()) {
            editTextPrenom.setError(getString(R.string.input_error_prenom));
            editTextPrenom.requestFocus();
            return;
        }

        if (carte.isEmpty()) {
            editTextCarte.setError(getString(R.string.input_error_carte));
            editTextCarte.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.input_error_email));
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.input_error_email_invalid));
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.input_error_password));
            editTextPassword.requestFocus();
            return;
        }

        if (confirm.isEmpty()) {
            editTextPassword.setError(getString(R.string.input_error_confirm));
            editTextPassword.requestFocus();
            return;
        }

        if (!password.equals(confirm)) {
            editTextConfirm.setError(getString(R.string.input_error_confirm2));
            editTextConfirm.requestFocus();
            return;
        }
         progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override

            public void onComplete(@NonNull Task< AuthResult > task) {



                if (task.isSuccessful()) {

                    User user = new User(
            /*                nom,
                            prenom,
                            carte,
                            email,
                            password  */

                    );


                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {


                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Inscription.this, "Inscription  réussie !!!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                            else {
                                    Toast.makeText(Inscription.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);

                            }
                            }

                    });

                } else {
                    Toast.makeText(Inscription.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inscription_valide_btn:
                registerUser();
                break;
        }
    }
}


