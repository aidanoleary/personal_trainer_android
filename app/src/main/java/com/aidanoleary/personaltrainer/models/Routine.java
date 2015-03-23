package com.aidanoleary.personaltrainer.models;

import java.util.ArrayList;

/**
 * Created by aidanoleary on 26/01/2015.
 */
public class Routine {

    private long id;
    private String name;
    private String description;
    private ArrayList<Workout> workouts;

    public Routine() {

    }

    public Routine(String name, String description, ArrayList<Workout> workouts) {
        this.name = name;
        this.description = description;
        this.workouts = workouts;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWorkouts(ArrayList<Workout> workouts) {
        this.workouts = workouts;
    }


    // Getters
    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

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
