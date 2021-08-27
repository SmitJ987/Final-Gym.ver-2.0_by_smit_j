package com.example.finalgym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class AllTrainingsActivity extends AppCompatActivity {

    private RecyclerView allTrainingsRecView;

    private TrainingRecViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trainings);

        initViews();
        adapter = new TrainingRecViewAdapter(this);
        allTrainingsRecView.setLayoutManager(new GridLayoutManager(this,2));


        allTrainingsRecView.setAdapter(adapter);


        if(Utils.getInstance(this).getAllTrainings()!=null)
        {
            adapter.setTrainings(Utils.getInstance(this).getAllTrainings());
        }


    }

    private void initViews()
    {
        allTrainingsRecView = findViewById(R.id.allTrainingsRecView);
    }
}
