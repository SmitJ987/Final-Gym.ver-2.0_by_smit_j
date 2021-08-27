package com.example.finalgym;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import static android.content.Context.ALARM_SERVICE;
import static com.example.finalgym.TrainingActivity.TRAINING_KEY;

public class PlanRecViewAdapter extends RecyclerView.Adapter<PlanRecViewAdapter.ViewHolder>{

    private ArrayList<Plan> allPlans = new ArrayList<>();

    private Context mContext;

    private String type = "";

    public PlanRecViewAdapter(Context mContext)
    {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plan_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtName.setText(allPlans.get(position).getTraining().getName());
        holder.txtDescription.setText(allPlans.get(position).getTraining().getShortDescription());
        holder.txtMinutes.setText(String.valueOf(allPlans.get(position).getMinutes())+" minute(s)");

        Glide.with(mContext)
                .asBitmap()
                .load(allPlans.get(position).getTraining().getImageUrl())
                .into(holder.trainingImage);

        if (allPlans.get(position).isAccomplished())
        {
            //so here the plan is Accomplished
            holder.emptyCircle.setVisibility(View.GONE);
            holder.checkedCircle.setVisibility(View.VISIBLE);

        }
        else
        {
            holder.emptyCircle.setVisibility(View.VISIBLE);
            holder.checkedCircle.setVisibility(View.GONE);
        }


        holder.planParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,TrainingActivity.class);
                intent.putExtra(TRAINING_KEY,allPlans.get(position).getTraining());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        if(getType().equals("edit"))
        {
            holder.checkedCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Plan thisPlan = allPlans.get(position);
                    Utils.getInstance(mContext).removePlan(allPlans.get(position));

                    thisPlan.setAccomplished(false);
                    Utils.getInstance(mContext).addPlan(thisPlan);
                    notifyDataSetChanged();
                }
            });

            holder.emptyCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                    builder.setTitle("Confirm").setMessage("Did you finished this training?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //saving this plan, as we have to change the accomplish state, and remove the old version
                                    //of this plan, and add new version of this plan, (i.e pdated accomplish state)
                                    Utils.getInstance(mContext).removePlan(allPlans.get(position));
                                    Plan thisPlan = allPlans.get(position);
                                    thisPlan.setAccomplished(true);
                                    Utils.getInstance(mContext).addPlan(thisPlan);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create().show();

                }
            });

            holder.planParent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("plan removal confirmation")
                            .setMessage("Do you really want to delete "+allPlans.get(position).getTraining().getName()+" from your week?")
                            .setNegativeButton("Keep", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(Utils.getInstance(mContext).removePlan(allPlans.get(position)))
                                    {
                                        Toast.makeText(mContext, allPlans.get(position).getTraining().getName()+" removed", Toast.LENGTH_SHORT).show();
                                        setAllPlans(Utils.getInstance(mContext).getPlansByDay(allPlans.get(position).getDay()));
                                        notifyDataSetChanged();
                                    }
                                    else
                                    {
                                        Toast.makeText(mContext, "can't remove the plan.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).create().show();
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return allPlans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView trainingImage;
        private ImageView checkedCircle, emptyCircle;
        private TextView txtName, txtMinutes, txtDescription;
        private RelativeLayout planParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trainingImage = itemView.findViewById(R.id.trainingImage);
            txtName = itemView.findViewById(R.id.txtName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtMinutes = itemView.findViewById(R.id.txtMinutes);

            checkedCircle = itemView.findViewById(R.id.checkedCircle);
            emptyCircle = itemView.findViewById(R.id.emptyCircle);

            planParent = itemView.findViewById(R.id.plan_parent);
        }
    }

    public void setAllPlans(ArrayList<Plan> allPlans)
    {
        this.allPlans = allPlans;
        notifyDataSetChanged();
    }

    public void setType(String type)
    {
        this.type = type;
    }
    private String getType()
    {
        return type;
    }
}
