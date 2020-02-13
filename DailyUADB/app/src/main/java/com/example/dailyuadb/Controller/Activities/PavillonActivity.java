package com.example.dailyuadb.Controller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailyuadb.R;

public class PavillonActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String NAME_PAVILLON = "NAME_PAVILLON";
    Button mPavillonAButton;
    Button mPavillonBButton;
    Button mPavillonCButton;
    Button mPavillonDButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pavillon);


        // Wire widgets
        mPavillonAButton = (Button) findViewById(R.id.pavillonA_btn);
        mPavillonBButton = (Button) findViewById(R.id.pavillonB_btn);
        mPavillonCButton = (Button) findViewById(R.id.pavillonC_btn);
        mPavillonDButton = (Button) findViewById(R.id.pavillonD_btn);

        mPavillonAButton.setOnClickListener(this);
        mPavillonBButton.setOnClickListener(this);
        mPavillonCButton.setOnClickListener(this);
        mPavillonDButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, CodifiantActivity.class);
        switch (view.getId()) {
            case R.id.pavillonA_btn:
                intent.putExtra(NAME_PAVILLON,"PAVILLON A");
                break;
            case R.id.pavillonB_btn:
                intent.putExtra(NAME_PAVILLON,"PAVILLON B");
                break;
            case R.id.pavillonC_btn:
                intent.putExtra(NAME_PAVILLON,"PAVILLON C");
                break;
            case R.id.pavillonD_btn:
                intent.putExtra(NAME_PAVILLON,"PAVILLON D");
                break;

        }
        startActivity(intent);

    }
}
