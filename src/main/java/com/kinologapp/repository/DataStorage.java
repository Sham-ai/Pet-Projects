package com.kinologapp.repository;

import com.kinologapp.model.payment.Payment;
import java.util.ArrayList;
import com.kinologapp.model.entity.User;
import java.util.List;


public class DataStorage {
   //список хранения все успешных платежей
    private static List<Payment> payments = new ArrayList<>();

    //список хранения всех пользователей(тренеров и клиентов)
    private static List<User> users = new ArrayList<>();

    //метод для сохранения платежей
    public static void  addPayment(Payment payment){
        payments.add(payment);
    }

    //метод для сохранения пользователей
    public static void  addUser(User user){
        users.add(user);
    }

    // методы для получения данных
    public static List<Payment> getAllPayments(){
        return payments;
    }
}
