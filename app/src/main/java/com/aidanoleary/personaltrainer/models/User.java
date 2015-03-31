package com.aidanoleary.personaltrainer.models;

/**
 * Created by aidanoleary on 14/01/2015.
 * A model for the User
 */
public class User {
    private long id;
    private String email;
    private int age;
    private String gender;
    private double height;
    private double weight;
    private double bmi;
    private int fitnessLevel;
    private String authorizationToken;
    private Routine routine;
    private int points;
    private int totalReps;
    private int totalSets;
    private double totalWeight;
    private double totalCardio;
    private double totalDistance;

    // constructors
    public User() {
        id = 0;
        email = "";
        authorizationToken = "";
        age = 0;
        gender = "";
        height = 0;
        weight = 0;
        routine = null;
        points = 0;
        totalSets = 0;
        totalReps = 0;
        totalWeight = 0;
        totalCardio = 0;
        totalDistance = 0;

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

    public User(String email, int age, String gender, Routine routine) {
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.routine = routine;
    }

    public User(String email, String authorizationToken, int age, String gender, Routine routine, int points) {
        this.email = email;
        this.authorizationToken = authorizationToken;
        this.age = age;
        this.gender = gender;
        this.routine = routine;
        this.points = points;
    }

    // setters
    public void setId(long id) {
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

    public void setPoints(int points) {
        this.points = points;
    }


    public void setTotalReps(int totalReps) {
        this.totalReps = totalReps;
    }


    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setTotalCardio(double totalCardio) {
        this.totalCardio = totalCardio;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void setTotalSets(int totalSets) {
        this.totalSets = totalSets;
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

    public int getPoints() {
        return points;
    }

    public int getTotalReps() {
        return totalReps;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getTotalCardio() {
        return totalCardio;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public int getTotalSets() {
        return totalSets;
    }
}
