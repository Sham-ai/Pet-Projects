package com.kinologapp.model.entity;
import com.kinologapp.util.Notifiable;

public abstract class User implements Notifiable {
    private long id;
    private String firstName;
    private String phoneNumber;

    public User(long id, String firstName, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("Уведомление для " + firstName + ": " + message);
    }

    public void introduce() {
        System.out.println("Привет, меня зовут " + firstName);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }








}
