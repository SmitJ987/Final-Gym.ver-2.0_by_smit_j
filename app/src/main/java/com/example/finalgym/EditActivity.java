package com.example.finalgym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    private TextView txtDayName;
    private RecyclerView editPlansForPresentDayRecView;
    private PlanRecViewAdapter adapter;
    private Button btnAddMorePlans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        txtDayName = findViewById(R.id.txtDayName);
        btnAddMorePlans = findViewById(R.id.btnAddMorePlans);
        editPlansForPresentDayRecView = findViewById(R.id.editPlansForPresentDayRecView);


        Intent intent = getIntent();
        if(intent!=null)
        {
            String day = intent.getStringExtra("day");
            if(day!=null)
            {
                txtDayName.setText(day);
                if(Utils.getInstance(this).getPlansByDay(day).size()>0)
                {
                    adapter = new PlanRecViewAdapter(this);
                    adapter.setAllPlans(Utils.getInstance(this).getPlansByDay(day));

                    editPlansForPresentDayRecView.setAdapter(adapter);
                    editPlansForPresentDayRecView.setLayoutManager(new GridLayoutManager(this,2));
                    adapter.setType("edit");
                }

            }
        }

        btnAddMorePlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this,AllTrainingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
