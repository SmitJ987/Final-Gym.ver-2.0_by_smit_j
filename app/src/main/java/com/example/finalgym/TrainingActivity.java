package com.example.finalgym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;




//step 6.5.7 of PlanDetailDialog.java file: to implement the PassPlanInterface here
public class TrainingActivity extends AppCompatActivity implements PlanDetailsDialog.PassPlanInterface {
    
    //implementing method of PassPlanInterface:  part of step 6.5.7 of PlanDetailDialog.java
    @Override
    public void getPlan(Plan plan) {
        Log.d(TAG, "getPlan: "+plan.toString());

        //as we have now got the plan from fragment: i.e PlanDetailsDialog, we can now add this
        //Plan to Utils's allPlans AL
        if(Utils.getInstance(this).addPlan(plan))
        {
            Toast.makeText(this, plan.getTraining().getName()+" added successfully", Toast.LENGTH_SHORT).show();
            //now navigate the user to AllPlansActivity
            Intent intent=new Intent(this,AllPlansActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }

    }

    private static final String TAG = "TrainingActivity";

    private ImageView trainingImage;
    private TextView txtDescription, txtName;

    private Button btnAddToPlans;

    public static final String TRAINING_KEY = "training";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        initViews();

        //now here we want to get the Training from the Intent
        Intent intent = getIntent();
        //so for this we also have to put the Training in putExtra, when we are clicking on parentCardView
        //inside the AllTrainingActivity
        final Training presentTraining = intent.getParcelableExtra(TRAINING_KEY);
        if(presentTraining!=null)
        {
            txtName.setText(presentTraining.getName());
            txtDescription.setText(presentTraining.getShortDescription());
            Glide.with(this).asBitmap().load(presentTraining.getImageUrl()).into(trainingImage);

            btnAddToPlans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //here we want to add this Training to our Plans list in Utils
                    //for that we will
                    //also when this button is clicked, we have to show a dialog
                    //in which we will show name of training, and ask for days and minutes to enter
                    //so for that making, dialog_plan_details.xml
                    // user will enter the

                    //to show the PlanDetailDialog, we have to make it here
                    //so coming here after i have making the: dialog_plan_details.xml, and PlanDetailDialog.java
                    PlanDetailsDialog dialog = new PlanDetailsDialog();

                    //now we have to set the arguments for this dialog, this is the reason
                    //why we used Bundle = getArguments() inside the PlanDetailsDialog.java
                    //here we setArgument in the Parent activity, and in the Fragment we getArgument
                    Bundle bundle = new Bundle();
                    //now inside the bundle, put the training as parcelable
                    bundle.putParcelable(TRAINING_KEY,presentTraining);
                    //now place it in setArgument
                    dialog.setArguments(bundle);
                    //and finally show the dialog
                    dialog.show(getSupportFragmentManager(),"plan details dialog");
                }
            });
        }

        // i would not do below code, it is easy and for beginners, do the above code
        //i.e putExtra in intent of TrainingRecViewAdapter and getExtraParcelable in TrainingActivity
        //and to that, implementing Parcelable inside the Training model (POJO)

        /*int id=intent.getIntExtra(TRAINING_KEY,-1);
        if(id!=-1)
        {
            for(Training t: Utils.getAllTrainings())
            {
                if(t.getId()==id)
                {
                    //Training currentTraining = Utils.getAllTrainings().get(id);
                    Training currentTraining = t;
                    txtName.setText(currentTraining.getName());
                    txtDescription.setText(currentTraining.getShortDescription());

                    Glide.with(this).asBitmap().load(currentTraining.getImageUrl()).into(trainingImage);
                }
            }
        }*/
    }

    //making initViews()
    private void initViews()
    {
        trainingImage = findViewById(R.id.trainingImage);
        txtDescription = findViewById(R.id.txtDescription);
        txtName = findViewById(R.id.txtName);

        btnAddToPlans = findViewById(R.id.btnAddToPlans);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
