package com.aidanoleary.personaltrainer.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by aidanoleary on 11/02/2015.
 * This is a singleton class that will be used to store the main program
 * data structure in memory as long as the application is running. It will
 * then be able to be accessed from any activity and fragment.
 */
public class MainSingleton {

    private static MainSingleton sMainSingleton;
    private Context mAppContext;
    private SharedPreferences mSharedPreferences;
    private User mUser;

    private MainSingleton(Context appContext) {
        mAppContext = appContext;
        //mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);

        mSharedPreferences = appContext.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);

        // Initialise all the components of the main data structure.
        //mUser.setEmail(mSharedPreferences.getString("Email", ""));


        // Create 6 exercises
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        for (int i = 0; i < 6; i++) {
            exercises.add(new Exercise("test exercise " + i));
        }

        // Create 3 workouts and exercises to them
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        for (int i = 0; i < 6; i++) {
            workouts.add(new Workout("test workout " + i, "test description " + i, "day " + i));
            workouts.get(i).addExerciseList(exercises);
        }


        // create a routine and add workouts to it
        Routine routine = new Routine("Workout routine 1", "This is the description of the routine", workouts);
        mUser = new User(mSharedPreferences.getString("Email", ""), mSharedPreferences.getString("AuthToken", ""), 22, "male", routine, 100);
        mUser.setAuthorizationToken(mSharedPreferences.getString("AuthToken", ""));

        // Change this to use the data in the sqlite database

    }

    public static MainSingleton get(Context c) {

        // Check if the singleton has already been initialised
        if(sMainSingleton == null) {
            // Initialise the singleton with the application context.
            sMainSingleton = new MainSingleton(c.getApplicationContext());
        }
        return sMainSingleton;
    }

    public static void Destroy() {
        sMainSingleton = null;
    }

    // ---- getters -----

    public User getUser() {
        return mUser;
    }


    // ---- setters -----
    public void setUser(User mUser) {
        this.mUser = mUser;
    }
}
