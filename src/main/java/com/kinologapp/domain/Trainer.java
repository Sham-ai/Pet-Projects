package com.kinologapp.domain;

public class Trainer extends User {

    private int experienceYears;
    private int startWorkHour = 8;
    private int endWorkHour = 22;

    public Trainer(long id, String firstName, String phoneNumber, int experienceYears) {
        super(id, firstName, phoneNumber);
        this.experienceYears = experienceYears;
    }

    public int getStartWorkHour() {
        return startWorkHour;
    }

    public void setStartWorkHour(int startWorkHour) {
        this.startWorkHour = startWorkHour;
    }

    public int getEndWorkHour() {
        return endWorkHour;
    }

    public void setEndWorkHour(int endWorkHour) {
        this.endWorkHour = endWorkHour;
    }

    @Override
    public void introduce(){
        System.out.println("Я кинолог " + getFirstName() + ". Стаж работы: " + experienceYears + " лет");
    }
}
