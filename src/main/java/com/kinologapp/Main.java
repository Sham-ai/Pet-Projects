package com.kinologapp;

import com.kinologapp.model.entity.Client;
import com.kinologapp.model.entity.User;
import com.kinologapp.model.enums.PaymentType;
import com.kinologapp.model.payment.Payment;
import com.kinologapp.repository.DataStorage;
import com.kinologapp.service.PaymentService;
import com.kinologapp.service.StatisticsService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        // 1. Подготовка данных (Инициализация)
        initTestData();

        // 2. Логика работы приложения
        processClientPayments();

        // 3. Вывод аналитики (отчет)
        showStatistics();
    }

    private static void initTestData() {
        System.out.println("--- Загрузка данных ---");
        // Создаем системного пользователя (Клуб), который будет принимать деньги
        User clubBase = new User(0L, "Главный офис Клуба", "+79000000000");

        System.out.println("Система готова. Данные загружены.");
    }

    private static void processClientPayments() {
        System.out.println("--- Работа с клиентами и оплатой ---");

        // 1. Создаем клиента (sender)
        Client ivan = new Client(1L, "Иван", "+79991234567", "DOG-555");
        DataStorage.addClient(ivan);

        // 2. Создаем получателя
        User club = new User(0L, "Кинологический Клуб", "+79869249688");

        // 3. Создаем сумму через BigDecimal
        BigDecimal price = new BigDecimal("1500.00");

        // 4. Формируем платеж
        Payment payment = new Payment(ivan, club, price, PaymentType.PACKAGE);

        // 5. Отправляем в сервис на обработку
        PaymentService.processPayment(payment);
    }

    private static void showStatistics() {
        System.out.println("\n--- Финансовые показатели ---");
        StatisticsService.printFullReport();
    }
}