package com.example.finalgym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAbout, btnAllTrainings, btnMyPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        //initialising allTrainings AL, with help of Utils class
        //Utils.initTrainings();

        btnAllTrainings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AllTrainingsActivity.class);
                startActivity(intent);
            }
        });

        //onClickListener for btnMyPlans
        btnMyPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //from here we have to go to AllPlansActivity, let first make it
                Intent intent = new Intent(MainActivity.this,AllPlansActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews()
    {
        btnAllTrainings = findViewById(R.id.btnAllTrainings);
        btnMyPlans = findViewById(R.id.btnMyPlans);
        btnAbout = findViewById(R.id.btnAbout);
    }



}
