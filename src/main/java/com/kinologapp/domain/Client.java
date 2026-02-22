package com.kinologapp.domain;

public class Client extends User {

    private String dogCardNumber; //номер карточки

    public Client(long id, String firstName, String phoneNumber, String dogCardNumber) {
        super(id, firstName, phoneNumber);
        this.dogCardNumber = dogCardNumber;
    }

    @Override
    public void introduce(){
        System.out.println("Я клиент " + getFirstName() + ". Номер карточки: " + dogCardNumber);
    }
}
