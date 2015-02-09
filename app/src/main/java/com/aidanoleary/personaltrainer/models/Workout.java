package com.aidanoleary.personaltrainer.models;

import java.util.ArrayList;

/**
 * Created by aidanoleary on 25/01/2015.
 */
public class Workout {

    private int id;
    private int routineId;
    private String name;
    private String description;
    private String day;
    private ArrayList<Exercise> exercises;


    // Constructors for the workout class
    public Workout(String name, String description, String day) {
        this.name = name;
        this.description = description;
        this.day = day;
    }

    public Workout(String name, String day, ArrayList<Exercise> exercises) {
        this.name = name;
        this.day = day;
        this.exercises = exercises;
    }

    public Workout(int id, String name, String day, ArrayList<Exercise> exercises) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.exercises = exercises;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public int getRoutineId() {
        return routineId;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    // Add a list of exercises to the workout
    public void addExerciseList(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    // Add an exercise to the workout.
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    // Retrieve an exercise.
    public Exercise getExercise(int index) {
        return exercises.get(index);
    }

}
