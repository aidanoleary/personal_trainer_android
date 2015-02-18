package com.aidanoleary.personaltrainer.models;

/**
 * Created by aidanoleary on 17/02/2015.
 * TODO maybe get rid of this class and user workout class
 */
public class UserWorkoutExercise {
    private int id;
    private int userWorkoutId;
    private int exerciseId;
    private int numberOfReps;
    private int numberOfSets;
    private int weight;


    // Constructors
    public UserWorkoutExercise(int id, int userWorkoutId, int exerciseId, int numberOfReps, int numberOfSets, int weight) {
        this.id = id;
        this.userWorkoutId = userWorkoutId;
        this.exerciseId = exerciseId;
        this.numberOfReps = numberOfReps;
        this.numberOfSets = numberOfSets;
        this.weight = weight;
    }

    public UserWorkoutExercise(int userWorkoutId, int exerciseId, int numberOfReps, int numberOfSets, int weight) {
        this.userWorkoutId = userWorkoutId;
        this.exerciseId = exerciseId;
        this.numberOfReps = numberOfReps;
        this.numberOfSets = numberOfSets;
        this.weight = weight;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserWorkoutId(int userWorkoutId) {
        this.userWorkoutId = userWorkoutId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setNumberOfReps(int numberOfReps) {
        this.numberOfReps = numberOfReps;
    }

    public void setNumberOfSets(int numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    // Getters
    public int getId() {
        return id;
    }

    public int getUserWorkoutId() {
        return userWorkoutId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public int getNumberOfReps() {
        return numberOfReps;
    }

    public int getNumberOfSets() {
        return numberOfSets;
    }

    public int getWeight() {
        return weight;
    }
}
