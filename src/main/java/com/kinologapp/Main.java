package com.kinologapp;

import com.kinologapp.model.appointment.Appointment;
import com.kinologapp.model.entity.ClientProfile;
import com.kinologapp.model.entity.TrainerProfile;
import com.kinologapp.model.entity.User;
import com.kinologapp.model.enums.PaymentType;
import com.kinologapp.model.enums.Role;
import com.kinologapp.model.payment.Payment;
import com.kinologapp.repository.DataStorage;
import com.kinologapp.service.BookingService;
import com.kinologapp.service.PaymentService;
import com.kinologapp.service.StatisticsService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Загрузка данных ---");

        User club = new User(0L, "Кинологический Клуб", "+79000000000");
        club.addRole(Role.DIRECTOR);
        DataStorage.addUser(club);

        User trainer = new User(2L, "Анна", "+79990000002");
        trainer.enableTrainer(new TrainerProfile(5, 9, 18));
        DataStorage.addUser(trainer);

        User client = new User(1L, "Иван", "+79991234567");
        client.enableClient(new ClientProfile(true)); // VIP
        DataStorage.addUser(client);

        System.out.println("Система готова. Данные загружены.\n");

        BookingService bookingService = new BookingService();
        LocalDateTime tomorrow10 = LocalDateTime.now()
                .plusDays(1)
                .withHour(10).withMinute(0).withSecond(0).withNano(0);

        Appointment appointment = new Appointment(client, trainer, tomorrow10);
        bookingService.createBooking(appointment);

        System.out.println("\n--- Оплата занятия ---");
        BigDecimal price = new BigDecimal("1000.00");
        Payment payment = new Payment(client, trainer, price, PaymentType.SINGLE_LESSON);

        PaymentService paymentService = new PaymentService();
        paymentService.processPayment(payment);

        System.out.println("\n--- Финансовые показатели ---");
        StatisticsService.printFullReport();
    }
}