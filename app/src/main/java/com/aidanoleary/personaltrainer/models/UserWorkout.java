package com.aidanoleary.personaltrainer.models;

import java.util.Date;

/**
 * Created by aidanoleary on 17/02/2015.
 * This is a model used to represent a user workout
 */
public class UserWorkout {

    private int id;
    private int userId;
    private int workoutId;
    private Date workoutDate;
    private double timeTaken;

    public UserWorkout(int id, int userId, int workoutId, Date workoutDate) {
        this.id = id;
        this.userId = userId;
        this.workoutId = workoutId;
        this.workoutDate = workoutDate;
    }

    public UserWorkout(int userId, int workoutId, Date workoutDate) {
        this.userId = userId;
        this.workoutId = workoutId;
        this.workoutDate = workoutDate;
    }

    // Setters
    public void setTimeTaken(double timeTaken) {
        this.timeTaken = timeTaken;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public void setWorkoutDate(Date workoutDate) {
        this.workoutDate = workoutDate;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public Date getWorkoutDate() {
        return workoutDate;
    }

    public double getTimeTaken() {
        return timeTaken;
    }
}
