package com.aidanoleary.personaltrainer.models;

import java.util.ArrayList;

/**
 * Created by aidanoleary on 26/01/2015.
 */
public class Routine {

    private String name;
    private ArrayList<Workout> workouts;

    public Routine(String name, ArrayList<Workout> workouts) {
        this.name = name;
        this.workouts = workouts;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setWorkouts(ArrayList<Workout> workouts) {
        this.workouts = workouts;
    }


    // Getters
    public String getName() {
        return this.name;
    }

    public ArrayList<Workout> getWorkouts() {
        return this.workouts;
    }

    // Add a workout to the routine's workouts
    public void addWorkout(Workout workout) {
        workouts.add(workout);
    }
}
