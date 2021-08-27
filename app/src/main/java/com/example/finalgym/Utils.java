package com.example.finalgym;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {


    //private static ArrayList<Training> allTrainings;
    //private static ArrayList<Plan> allPlans;
    private static ArrayList<String> allDays;

    private String sharedPrefFile = "com.example.android.finalgym";

    //SharedPreferences db = new SharedPreferences

    //now instead of using static AL, using the SharedPreferences
    private SharedPreferences sharedPreferences;

    //keys for the AL in sharedPreferences
    public static final String ALL_TRAINING_AL_KEY = "all_trainings";
    public static final String ALL_PLANS_AL_KEY = "all_plans";
    //public static final String ALL_DAYS_AL_KEY = "all_days";

    //defining the static instance of this class
    private static Utils instance;


    private Utils(Context mContext)
    {
        sharedPreferences = mContext.getSharedPreferences("gym_db", Context.MODE_PRIVATE);


        initTrainings();

    }

    public void initTrainings()
    {


        ArrayList<Training> allTrainings = new ArrayList<>();
        Training pushUp = new Training("Push Up","ye hai chhote push up ka" +
                "chota description","aur ye hai lamba information",
                "https://image.shutterstock.com/image-photo/picture-young-athletic-man-doing-260nw-461264842.jpg",1);
        allTrainings.add(pushUp);

        Training squat = new Training("Squat","if you crouch down very low and sit on your heels, you Squat"
                ,"a squat is a strenght exercise in which a trainee lower their hips from a standing position" +
                "and then stands back"
                ,"https://image.shutterstock.com/image-photo/athletic-man-doing-squats-preparing-260nw-1045041430.jpg",2);
        allTrainings.add(squat);

        Training legPress = new Training("Leg Press","The leg press is pressing legs inwards"
                ,"the leg press is a weight training exercise in which the individual pushes the wight or resistance away from them",
                "https://image.shutterstock.com/image-illustration/inclined-leg-press-3d-illustration-260nw-434632384.jpg",3);
        allTrainings.add(legPress);

        Training burpees = new Training("Burpees","Start by standing upright with your feet shoulder-width apart and your arms down at your sides"
                ,"Jump your feet up to your palms by hinging at the waist. Get your feet as close to your hands as you can get, landing them outside your hands if necessary.",
                "https://image.shutterstock.com/image-vector/exercise-guide-woman-doing-squat-260nw-1350246731.jpg",4);
        allTrainings.add(burpees);

        Training pullUps = new Training("PullUps","It’s the ultimate test of upper-body muscular strength and one of the very few bodyweight moves that works your back and biceps",
                "The best way to build pull-up power is by doing wide-grip lat pull-downs, both heavy-weight sets and high-rep sets,” says Lerwill. “Eccentric pull-ups – where you ‘jump’ to the top position and lower back down very slowly – are also very good training drills",
                "https://image.shutterstock.com/image-vector/l-sit-hang-on-bar-600w-796165771.jpg",5);
        allTrainings.add(pullUps);



        Gson gson = new Gson();
        //todo: bug below
        //getInstance().sharedPreferences = new SharedPreferences.Editor;
        //initialising the shared preference:

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Type type = new TypeToken<ArrayList<Training>>(){}.getType();
        editor.putString(ALL_TRAINING_AL_KEY, gson.toJson(allTrainings,type));
        editor.apply();

        //initiating the days of the plans
        if(allDays==null)
        {
            allDays = new ArrayList<>();
            allDays.add("Monday");
            allDays.add("Tuesday");
            allDays.add("Wednesday");
            allDays.add("Thursday");
            allDays.add("Friday");
            allDays.add("Saturday");
            allDays.add("Sunday");
        }

        if (getAllPlans()==null)
        {
            //allPlans = new ArrayList<>();
            //as we have already defined the editor and gson, above in this scope, so no need to define them again
            ArrayList<Plan> allPlans = new ArrayList<>();
            editor.putString(ALL_PLANS_AL_KEY,gson.toJson(allPlans));
            editor.commit();
        }
    }

    public  ArrayList<Training> getAllTrainings()
    {
        //return allTrainings
        ArrayList<Training> allTrainings = new ArrayList<>();
        //SharedPreferences.Editor editor= getInstance().sharedPreferences.edit();
        Gson gson = new Gson();
        //and the TypeToken
        Type type = new TypeToken<ArrayList<Training>>(){}.getType();

/*        if(Utils.getInstance().sharedPreferences.contains(ALL_TRAINING_AL_KEY))
        {



        }*/
        allTrainings = gson.fromJson(sharedPreferences.getString(ALL_TRAINING_AL_KEY,null),type);
        return allTrainings;
    }


    public ArrayList<Plan> getAllPlans()
    {
        //return allPlans;
        //also here we do not need editor of SharedPreferences, since we are only getting data, putting nothing new inside
        //the SharedPreferences
        //-->SharedPreferences.Editor editor = Utils.getInstance().sharedPreferences.edit();
        Gson gson = new Gson();

        ArrayList<Plan> allPlans;
        Type type = new TypeToken<ArrayList<Plan>>(){}.getType();
        allPlans = gson.fromJson(sharedPreferences.getString(ALL_PLANS_AL_KEY,null),type);
        return allPlans;
    }

    //methods for adding and removing plan
    public boolean addPlan(Plan plan)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        //ArrayList allPlans = gson.fromJson(Utils.getInstance().sharedPreferences.getString(ALL_PLANS_AL_KEY,null));
        Type type = new TypeToken<ArrayList<Plan>>(){}.getType();
        ArrayList allPlans = gson.fromJson(sharedPreferences.getString(ALL_PLANS_AL_KEY,null),type);
        allPlans.add(plan);
        editor.remove(ALL_PLANS_AL_KEY);
        editor.putString(ALL_PLANS_AL_KEY,gson.toJson(allPlans));
        editor.apply();

        return true;
        //return allPlans.add(plan);
    }

    public boolean removePlan(Plan plan)
    {

        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //return allPlans.remove(plan);
        Type type = new TypeToken<ArrayList<Plan>>(){}.getType();
        ArrayList<Plan> allPlans = gson.fromJson(sharedPreferences.getString(ALL_PLANS_AL_KEY,null),type);

        for(Plan p: allPlans)
        {
            if(p.getDay().equalsIgnoreCase(plan.getDay()) && p.getTraining().getId()==plan.getTraining().getId() && p.getMinutes()==plan.getMinutes())
            {
                if(allPlans.remove(p))
                {
                    editor.remove(ALL_PLANS_AL_KEY);
                    editor.commit();
                    editor.putString(ALL_PLANS_AL_KEY,gson.toJson(allPlans,type));
                    editor.commit();
                    return true;
                }
            }
        }
        return false;
        /*for(Plan p: allPlans)
        {
            if(p.equals(plan))
            {
                return allPlans.remove(plan);
            }
        }*/
    }

    public static ArrayList<String> getAllDays()
    {
        return allDays;
    }

    public ArrayList<Plan> getPlansByDay(String day)
    {
        ArrayList<Plan> presentDayPlans = new ArrayList<>();
        for (Plan p: getAllPlans())
        {
            if(p.getDay().equalsIgnoreCase(day))
            {
                presentDayPlans.add(p);
            }
        }
        return presentDayPlans;
    }

    public static Utils getInstance(Context context)
    {
        if(instance==null)
        {
            instance = new Utils(context);
            return instance;
        }
        return instance;
    }

}
