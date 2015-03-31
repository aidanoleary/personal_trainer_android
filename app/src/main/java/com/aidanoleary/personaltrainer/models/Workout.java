package com.aidanoleary.personaltrainer.models;

import java.util.ArrayList;

/**
 * Created by aidanoleary on 25/01/2015.
 */
public class Workout {

    private long id;
    private long routineId;
    private String name;
    private String description;
    private String day;
    private ArrayList<Exercise> exercises;
    private boolean isSaved;


    // Constructors for the workout class
    public Workout() {
        this.id = 0;
        this.routineId = 0;
        this.name = "";
        this.description = "";
        this.day = "";
        this.exercises = new ArrayList<Exercise>();
        this.isSaved = false;
    }

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

    public Workout(long id, String name, String day, ArrayList<Exercise> exercises) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.exercises = exercises;
    }

    // Setter
    public void setId(long id) {
        this.id = id;
    }

    public void setRoutineId(long routineId) {
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

    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    // Getters
    public long getId() {
        return this.id;
    }

    public long getRoutineId() {
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

    public ArrayList<Exercise> getExerciseList() {
        return exercises;
    }

    public String getDay() {
        return day;
    }

    public boolean getIsSaved() {
        return isSaved;
    }
}
