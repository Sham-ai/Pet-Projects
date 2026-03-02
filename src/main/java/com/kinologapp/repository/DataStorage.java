package com.kinologapp.repository;

import com.kinologapp.model.payment.Payment;
import com.kinologapp.model.entity.Client;
import com.kinologapp.model.entity.Trainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class DataStorage {

    //Map для быстрого доступа по ID
    private static Map<Long, Client> clients = new HashMap<>();
    private static Map<Long, Trainer> trainers = new HashMap<>();

    //список хранения все успешных платежей
    private static List<Payment> payments = new ArrayList<>();

    //метод для сохранения платежей
    public static void  addPayment(Payment payment){
        payments.add(payment);
    }

    // методы для получения платежей
    public static List<Payment> getAllPayments(){
        return payments;
    }

    //метод для сохранения клиента
    public static void addClient(Client client){
        clients.put(client.getId(), client); // ID — это ключ, сам объект — значение
    }

    // метод для сохранения тренеров
    public static void addTrainer(Trainer trainer){
        trainers.put(trainer.getId(), trainer);
    }

    // метод для поиска
    public static Client getClient(long id){
        return clients.get(id);
    }

    public static Trainer getTrainer(long id){
        return trainers.get(id);
    }


}
