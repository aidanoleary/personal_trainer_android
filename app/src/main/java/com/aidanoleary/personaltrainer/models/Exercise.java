package com.aidanoleary.personaltrainer.models;

/**
 * Created by aidanoleary on 25/01/2015.
 */
public class Exercise {

    private String name;
    private String muscle;
    private String description;
    private double weight;
    private int reps;
    private int sets;

    // Constructors for the Exercise class
    public Exercise() {

    }

    public Exercise(String name, String muscle, String description, double weight, int reps, int sets) {
        this.name = name;
        this.muscle = muscle;
        this.description = description;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }


    // Getters
    public String getName() {
        return this.name;
    }

    public String getMuscle() {
        return this.muscle;
    }

    public String getDescription() {
        return this.description;
    }

    public double getWeight() {
        return this.weight;
    }

    public int getReps() {
        return this.reps;
    }

    public int getSets() {
        return this.sets;
    }


    // A method for increasing the weight by 2.5
    // Which is the average increase of weight in the gym

    public void increaseWeight() {
        weight += 2.5;
    }

}
