package com.example.finalgym;

public class Plan {

    //fields
    private Training training;
    //inside the planDescription we will use txtShortDescription from Training
    private boolean isAccomplished;
    private int minutes;
    private String day;

    //constructor of above fields
    public Plan(Training training,int minutes,String day,boolean isAccomplished)
    {
        this.training = training;
        this.minutes = minutes;
        this.day = day;
        this.isAccomplished = isAccomplished;
    }

    //getters and setters for above fields
    public void setTraining(Training training)
    {
        this.training = training;
    }
    public Training getTraining()
    {
        return training;
    }

    public void setMinutes(int minutes)
    {
        this.minutes = minutes;
    }

    public int getMinutes()
    {
        return minutes;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public String getDay()
    {
        return day;
    }

    public void setAccomplished(boolean isAccomplished)
    {
        this.isAccomplished = isAccomplished;
    }
    public boolean isAccomplished()
    {
        return isAccomplished;
    }

}
