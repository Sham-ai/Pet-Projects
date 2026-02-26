package com.kinologapp;

import com.kinologapp.model.appointment.Appointment;
import com.kinologapp.model.content.Article;
import com.kinologapp.model.content.Content;
import com.kinologapp.model.content.VideoLesson;
import com.kinologapp.model.entity.Client;
import com.kinologapp.model.entity.Trainer;
import com.kinologapp.model.enums.PaymentType;
import com.kinologapp.model.payment.Payment;
import com.kinologapp.repository.DataStorage;
import com.kinologapp.service.BookingService;
import com.kinologapp.service.PaymentService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

            // 1. Создаем людей
            Trainer alex = new Trainer(1, "Алексей", "8900...", 10, 9, 18);
            Client ivan = new Client(2, "Иван", "8911...", "DOG-123");

            // 2. Работаем с бронированием
            BookingService bookingService = new BookingService();
            Appointment app = new Appointment(ivan, alex, LocalDateTime.of(2026, 3, 1, 14, 0));
            bookingService.createBooking(app);

            // 3. Работаем с оплатой
            Payment p1 = new Payment(ivan, alex, new BigDecimal("5000.00"), PaymentType.PACKAGE);
            PaymentService paymentService = new PaymentService();
            paymentService.processPayment(p1);

            // 4. Смотрим статистику
            System.out.println("Всего платежей: " + DataStorage.getAllPayments().size());
        }
    }