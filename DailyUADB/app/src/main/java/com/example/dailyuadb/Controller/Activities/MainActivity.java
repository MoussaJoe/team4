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
import com.example.dailyuadb.Fragment.AjoutIdeeFragment;
import com.example.dailyuadb.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFrangment = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation_etudiant);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelected);

        Bundle intent= getIntent().getExtras();
        if (intent!= null){
            String publisher= intent.getString("publisherid");

            SharedPreferences.Editor editor= getSharedPreferences("PREFS",MODE_PRIVATE).edit();
            editor.putString("profileid",publisher);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();

        }else {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }

        /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();*/
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener  navigationItemSelected =
            new BottomNavigationView.OnNavigationItemSelectedListener(){

                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem){

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            //selectedFrangment = new HomeFragment();
                            selectedFrangment = new HomeFragment();
                            break;

                        case R.id.nav_boite_idee:
                            selectedFrangment = new AjoutIdeeFragment();
                            break;

                        /*case R.id.nav_add:
                            selectedFrangment = null;
                            startActivity(new Intent(MainActivity.this, PostActivity.class));
                            break;*/

                        case R.id.nav_menu_resto:
                            selectedFrangment = null;
                            startActivity(new Intent(MainActivity.this, ListeMenuActivity.class));
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
