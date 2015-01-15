package com.aidanoleary.personaltrainer;

/**
 * Created by aidanoleary on 14/01/2015.
 * A model for the User
 */
public class User {
    int id;
    String email;
    int age;
    String gender;
    String created_at;

    // constructors
    public User() {
    }

    public User(String email, int age, String gender) {
        this.email = email;
        this.age = age;
        this.gender = gender;
    }

    public User(int id, String email, int age, String gender) {
        this.id = id;
        this.email = email;
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

    public void setCreatedAt(String created_at){
        this.created_at = created_at;
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
}
