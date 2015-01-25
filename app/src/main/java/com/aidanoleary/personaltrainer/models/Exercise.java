package com.aidanoleary.personaltrainer.models;

/**
 * Created by aidanoleary on 25/01/2015.
 */
public class Exercise {

    private String name;
    private String muscle;
    private String description;

    // Constructors for the Exercise class
    public Exercise() {

    }

    public Exercise(String name, String muscle, String description) {
        this.name = name;
        this.muscle = muscle;
        this.description = description;
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
}
