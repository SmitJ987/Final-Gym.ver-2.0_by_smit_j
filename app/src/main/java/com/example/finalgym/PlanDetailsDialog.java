package com.example.finalgym;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

import static com.example.finalgym.TrainingActivity.TRAINING_KEY;

//step 1: extending it to DialogFragment
public class PlanDetailsDialog extends DialogFragment {


    //step 6.5.3: making the interface to pass a plan to TrainingActivity
    public interface PassPlanInterface
    {
        void  getPlan(Plan plan);
    }

    //step 6.5.4: instantiating the PassPlanInterface:
    private PassPlanInterface passPlanInterface;
    //step 6.5.5 would be to initialise this passPlanInterface inside the btnAdd button of onCreateDialog of this file

    //step 3.1 defining UI elements of dialog_plan_details.xml
    private TextView txtTrainingName;
    private EditText edtTxtMinutes;
    private Spinner spinnerDays;
    private Button btnDismiss,btnAdd;



    //step 2: overriding the onCreateDialog() method

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //step 2.1: creating the View object, we are making this as we will need it while
        // making the initViews() method:
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_plan_details,null);

        //step 2.2 calling initViews() method
        initViews(view);

        //step 4: creating AlertDialog
        AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity())
                .setView(view);

        //step 6: in order to get the incoming Training, iam going to receive that via the arguments
        //of this Fragment

        //step 6.1: getting the Bundle from the getArguments() bundle, (we had put this bundle with setArguments inside the TrainingActivity)
        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            //step 6.2: as bundle is not null, get the Training object from it
            final Training presentTraining = bundle.getParcelable(TRAINING_KEY);

            //object should not be null
            if(presentTraining!=null)
            {
                //step 6.3
                txtTrainingName.setText(presentTraining.getName());

                //step 6.4
                btnDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });

                //step 6.5
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //here is the main task:
                        //step 6.5.1
                        //first of all get the selected day, and the minutes from EditText
                        String day = spinnerDays.getSelectedItem().toString();
                        int minutes = Integer.parseInt(edtTxtMinutes.getText().toString());

                        ////step 6.5.2: creating plan object:
                        Plan plan  = new Plan(presentTraining,minutes,day,false);

                        //now to pass the data from fragment to the parent activity:
                        //so here our parent Activity is: TrainingActivity
                        //i have to pass this plan there, but also
                        //to pass this, we have to make a callBack interface above in this file:
                        //so that is step 6.5.3

                        //step 6.5.5: initialising the above created passPlanInterface
                        //but it will be inside the try catch block, so no crash happens
                        try {
                            passPlanInterface = (PassPlanInterface) getActivity();
                            //so basically iam going to implement this passPlanInterface inside my
                            //Parent activity: TrainingActivity
                            //for that reason iam initialising the passPlanInterface with getActivity() method here

                            //step 6.5.6:
                            passPlanInterface.getPlan(plan);
                            //dismiss the dialog as we got the plan
                            dismiss();

                            //we will define this getPlan method inside the ParentActivity i.e the TrainingActivity

                            //step 6.5.7: now go to TrainingActivity and implement this PassPlanInterface and
                            //it's method: getPlan

                        }
                        catch (ClassCastException e)
                        {
                            e.printStackTrace();
                            dismiss();
                        }


                    }
                });
            }
        }


        //-->return super.onCreateDialog(savedInstanceState);
        //step 5: return builder.create instead of above: super vala
        return builder.create();
    }

    //step 3: making initViews() method
    private void initViews(View view)
    {
        edtTxtMinutes = view.findViewById(R.id.edtTxtMinutes);
        spinnerDays = view.findViewById(R.id.spinnerDays);
        txtTrainingName = view.findViewById(R.id.txtName);

        btnDismiss = view.findViewById(R.id.btnDismiss);
        btnAdd = view.findViewById(R.id.btnAdd);
    }
}
