package com.example.dailyuadb.Controller.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.dailyuadb.Fragment.HomeFragment;
import com.example.dailyuadb.Fragment.IdeeFragment;
import com.example.dailyuadb.Fragment.ProfileFragment;
import com.example.dailyuadb.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AccueilCodifiantActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFrangment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_codifiant);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelected);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new com.example.dailyuadb.Fragment.HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener  navigationItemSelected =
            new BottomNavigationView.OnNavigationItemSelectedListener(){

                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem){

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFrangment = new HomeFragment();
                            //selectedFrangment = null;
                            //startActivity(new Intent(AcceuilActivity.this, PostAdapter.class));
                            break;

                        case R.id.nav_search:
                            selectedFrangment = new IdeeFragment();
                            break;

                        case R.id.nav_add:
                            selectedFrangment = null;
                            //startActivity(new Intent(AcceuilActivity.this, PostActivity.class));
                            break;

                        case R.id.nav_campus:
                            selectedFrangment = null;
                            startActivity(new Intent(AccueilCodifiantActivity.this, CodifiantActivity.class));
                            break;

                        case R.id.nav_profile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getUid());
                            editor.apply();
                            selectedFrangment = new ProfileFragment();
                            break;
                    }

                    if (selectedFrangment != null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFrangment).commit();
                    }
                    return  true;
                }
            };
}
