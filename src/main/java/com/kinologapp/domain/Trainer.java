package com.kinologapp.domain;

public class Trainer extends User {

    private int experienceYears;

    public Trainer(long id, String firstName, String phoneNumber, int experienceYears) {
        super(id, firstName, phoneNumber);
        this.experienceYears = experienceYears;
    }

    @Override
    public void introduce(){
        System.out.println("Я кинолог " + getFirstName() + ". Стаж работы: " + experienceYears + " лет");
    }
}
