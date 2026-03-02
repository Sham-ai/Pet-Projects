package com.kinologapp.model.entity;

public class Client extends User {

    public String getDogCardNumber() {
        return dogCardNumber;
    }

    private String dogCardNumber; //номер карточки
    private boolean isVip; // для расчета скидок

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public Client(long id, String firstName, String phoneNumber, String dogCardNumber) {
        super(id, firstName, phoneNumber);
        this.dogCardNumber = dogCardNumber;
    }

    @Override
    public void introduce(){
        System.out.println("Я клиент " + getFirstName() + ". Номер карточки: " + dogCardNumber);
    }
}
