package com.example.finalgym;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DayRecViewAdapter extends RecyclerView.Adapter<DayRecViewAdapter.ViewHolder> {


    private Context mContext;

    private ArrayList<String> allDays = Utils.getAllDays();

    public DayRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        //holder.txtDayName.setText(allPlans.get(position).getDay());
        holder.txtDayName.setText(allDays.get(position));

        PlanRecViewAdapter adapter = new PlanRecViewAdapter(mContext);
        adapter.setAllPlans(Utils.getInstance(mContext).getPlansByDay(allDays.get(position)));


        holder.presentDayAllPlansRecView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
        holder.presentDayAllPlansRecView.setAdapter(adapter);

        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,EditActivity.class);
                intent.putExtra("day",allDays.get(position));
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //return allPlans.size();
        return allDays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //define the elements:
        private RecyclerView presentDayAllPlansRecView;
        private TextView txtDayName, txtEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //initialising the UI elements
            presentDayAllPlansRecView = itemView.findViewById(R.id.presentDayAllPlansRecView);
            txtDayName = itemView.findViewById(R.id.txtDayName);
            txtEdit  = itemView.findViewById(R.id.txtEdit);
        }
    }

    public void setAllDays(ArrayList<String> allDays)
    {
        this.allDays = allDays;
    }


}
