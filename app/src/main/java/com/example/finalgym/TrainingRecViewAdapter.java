package com.example.finalgym;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import static com.example.finalgym.TrainingActivity.TRAINING_KEY;


//extending the ViewHolder created inside this class
public class TrainingRecViewAdapter extends RecyclerView.Adapter<TrainingRecViewAdapter.ViewHolder> {

    //ArrayList of Trainings:
    private ArrayList<Training> trainings = new ArrayList<>();
    //context needed for Glide
    private Context mContext;

    //training key
    //this key must be inside the TrainingActivity, not here,it is wrong to put it in here
    //-->public static final String TRAINING_KEY = "training_key";
    //constructor to get the Context
    public TrainingRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    //implementing methods of ViewHolder extension
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_item,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.txtName.setText(trainings.get(position).getName());
        holder.txtDescription.setText(trainings.get(position).getShortDescription());
        Glide.with(mContext)
                .asBitmap()
                .load(trainings.get(position).getImageUrl())
                .into(holder.trainingImage);

        //setting an onClickListener for the parent, to go to the TrainingActivity
        holder.trainingParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,TrainingActivity.class);
                intent.putExtra(TRAINING_KEY,trainings.get(position));
                //here above we are getting error, when putting training inside the intent as Parcelable
                //the reason for this is we have not yet implemented Parcelable in the Training.java class
                //(POJO model of Training)
                //so implementing Parcelable inside the Training.java, go there
                //now the error is gone
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //defining the Ui elements:
        private TextView txtDescription;
        private TextView txtName;
        private ImageView trainingImage;
        private MaterialCardView trainingParent;

        //placing the constructor, with help of alt+insert
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //initialising UI elements
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtName = itemView.findViewById(R.id.txtName);
            trainingImage = itemView.findViewById(R.id.trainingImage);
            trainingParent = itemView.findViewById(R.id.trainingParent);
        }
    }

    public void setTrainings(ArrayList<Training> trainings)
    {
        this.trainings = trainings;
        notifyDataSetChanged();
    }
}
