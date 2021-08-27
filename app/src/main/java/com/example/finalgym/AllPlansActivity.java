package com.example.finalgym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class AllPlansActivity extends AppCompatActivity {

    private Button btnAddPlans;
    private RelativeLayout noPlansRelLayout;
    private RelativeLayout havePlansRelLayout;
    private RecyclerView allDayRecView;

    private DayRecViewAdapter dayRecViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_plans);

        initViews();

        btnAddPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllPlansActivity.this,AllTrainingsActivity.class);
                startActivity(intent);
            }
        });



        //todo: in process checking, so ==0, then complete it with >0
        if(Utils.getInstance(this).getAllPlans().size()>0)
        {
            noPlansRelLayout.setVisibility(View.GONE);
            havePlansRelLayout.setVisibility(View.VISIBLE);

            dayRecViewAdapter = new DayRecViewAdapter(this);
            allDayRecView.setLayoutManager(new LinearLayoutManager(this));
            dayRecViewAdapter.setAllDays(Utils.getAllDays());
            allDayRecView.setAdapter(dayRecViewAdapter);
        }
        else
        {
            //as we have no plans, so set the Visibility of noPlanRelLayout to visible
            //and the visibility of havPlansRelLayout to gone
            noPlansRelLayout.setVisibility(View.VISIBLE);
            havePlansRelLayout.setVisibility(View.GONE);
        }
    }

    private void initViews()
    {
        btnAddPlans = findViewById(R.id.btnAddPlans);
        noPlansRelLayout = findViewById(R.id.noPlansRelLayout);
        havePlansRelLayout = findViewById(R.id.havePlansRelLayout);

        allDayRecView = findViewById(R.id.allDayRecView);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
