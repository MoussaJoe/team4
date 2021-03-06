package com.example.dailyuadb.Controller.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.dailyuadb.Fragment.HomeFragment;
import com.example.dailyuadb.Fragment.AjoutIdeeFragment;
import com.example.dailyuadb.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AcceuilActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFrangment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

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

                        case R.id.nav_boite_idee:
                            selectedFrangment = new AjoutIdeeFragment();
                            break;

                        case R.id.nav_add:
                            selectedFrangment = null;
                            startActivity(new Intent(AcceuilActivity.this, PostActivity.class));
                            break;

                        case R.id.nav_menu_resto:
                            selectedFrangment = null;
                            startActivity(new Intent(AcceuilActivity.this, ListeMenuActivity.class));
                            break;

                        case R.id.nav_profile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getUid());
                            editor.apply();
                            selectedFrangment = null;
                            startActivity(new Intent(AcceuilActivity.this, ProfileActivity.class));
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
