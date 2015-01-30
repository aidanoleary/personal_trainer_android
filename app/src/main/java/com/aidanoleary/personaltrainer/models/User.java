package com.aidanoleary.personaltrainer.models;

/**
 * Created by aidanoleary on 14/01/2015.
 * A model for the User
 */
public class User {
    private int id;
    private String email;
    private int age;
    private String gender;
    private double height;
    private double weight;
    private double bmi;
    private int fitnessLevel;
    private String authorizationToken;
    private Routine routine;

    // constructors
    public User() {
    }

    public User(String email, int age, String gender) {
        this.email = email;
        this.age = age;
        this.gender = gender;
    }

    public User(int id, String email, String authorizationToken, int age, String gender) {
        this.id = id;
        this.email = email;
        this.authorizationToken = authorizationToken;
        this.age = age;
        this.gender = gender;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setFitnessLevel(int fitnessLevel) {
        this.fitnessLevel = fitnessLevel;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    // getters
    public long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public int getAge() {
        return this.age;
    }

    public String getGender() {
        return this.gender;
    }

    public String getAuthorizationToken() { return this.authorizationToken; }

    public int getFitnessLevel() {
        return this.fitnessLevel;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getBmi() {
        return bmi;
    }
}
