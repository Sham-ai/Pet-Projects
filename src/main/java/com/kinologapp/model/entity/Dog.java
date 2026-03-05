package com.kinologapp.model.entity;

/**
 * Dog — питомец.
 */
public class Dog {

    private String name;
    private int age;
    private String breed;

    private User owner;

    public Dog() {
    }

    public Dog(String name, int age, String breed, User owner) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}