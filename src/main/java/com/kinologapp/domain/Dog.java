package com.kinologapp.domain;

public class Dog {

    private String name;
    private int age;
    private String breed; //породa
    private Client client;

    public Dog() {

    }
    public Dog(String name, int age, String breed, Client client) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

