package com.aidanoleary.personaltrainer.models;

/**
 * Created by aidanoleary on 25/01/2015.
 */
public class Exercise {

    private long id;
    private int serverId;
    private String name;
    private String description;
    private String level;
    private String mainMuscle;
    private String otherMuscles;
    private String equipment;
    private String type;
    private String mechanics;
    private String imageUrl;
    private double weight;
    private int reps;
    private int sets;

    // Constructors for the Exercise class
    public Exercise() {
        this.name = "";
        this.id = 0;
        this.description = "";
        this.level = "";
        this.mainMuscle = "";
        this.otherMuscles = "";
        this.equipment = "";
        this.type = "";
        this.mechanics = "";
        this.imageUrl = "";
        this.weight = 0;
        this.reps = 0;
        this.sets = 0;
    }

    public Exercise(String name) {
        this.name = name;
    }

    public Exercise(String name, int id, String description, String level, String mainMuscle, String otherMuscles, String equipment, String type, String mechanics, String imageUrl, double weight, int reps, int sets) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.level = level;
        this.mainMuscle = mainMuscle;
        this.otherMuscles = otherMuscles;
        this.equipment = equipment;
        this.type = type;
        this.mechanics = mechanics;
        this.imageUrl = imageUrl;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
    }

    public Exercise(String name, String description, String level, String mainMuscle, String otherMuscles, String equipment, String type, String mechanics, String imageUrl, double weight) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.mainMuscle = mainMuscle;
        this.otherMuscles = otherMuscles;
        this.equipment = equipment;
        this.type = type;
        this.mechanics = mechanics;
        this.imageUrl = imageUrl;
        this.weight = weight;
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

    public void setLevel(String level) {
        this.level = level;
    }

    public void setMainMuscle(String mainMuscle) {
        this.mainMuscle = mainMuscle;
    }

    public void setOtherMuscles(String otherMuscles) {
        this.otherMuscles = otherMuscles;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMechanics(String mechanics) {
        this.mechanics = mechanics;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }


    // Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLevel() {
        return level;
    }

    public String getMainMuscle() {
        return mainMuscle;
    }

    public String getOtherMuscles() {
        return otherMuscles;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getType() {
        return type;
    }

    public String getMechanics() {
        return mechanics;
    }

    public String getImageUrl() {
        return imageUrl;
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

    public int getServerId() {
        return this.serverId;
    }


    // A method for increasing the weight by 2.5
    // Which is the average increase of weight in the gym.
    public void increaseWeight() {
        weight += 2.5;
    }

    // A method for decreasing the weight by 2.5
    // Which is the average decrease of weight in the gym.
    public void decreaseWeight() {
        weight -= 2.5;
    }

}
