package com.kinologapp.service;

import com.kinologapp.model.appointment.Appointment;
import com.kinologapp.model.entity.TrainerProfile;
import com.kinologapp.model.enums.AppointmentStatus;


import java.time.Duration;
import java.time.LocalDateTime;

public class BookingService {

    //логика бронирования
    public boolean createBooking(Appointment appointment, LocalDateTime now) {
        LocalDateTime sessionTime = appointment.getDateTime();

        if (sessionTime.isBefore(now)) {
            System.out.println("Ошибка бронирования: Нельзя осуществить запись задним числом!");
            return false;
        }

        TrainerProfile trainerProfile = appointment.getTrainer().getTrainerProfile();
        if (trainerProfile == null) {
            System.out.println("Ошибка: у выбранного тренера не заполнен TrainerProfile (рабочие часы неизвестны)");
            return false;
        }

        int hour = sessionTime.getHour();
        int start = trainerProfile.getStartWorkHour();
        int end = trainerProfile.getEndWorkHour();

        if (hour < start || hour >= end) {
            System.out.println("Ошибка: Тренер работает только с " + start + ":00 до " + end + ":00");
            return false;
        }

        appointment.setStatus(AppointmentStatus.SCHEDULED);
        System.out.println("Запись подтверждена на " + sessionTime);
        return true;
    }

    public boolean createBooking(Appointment appointment) {
        return createBooking(appointment, LocalDateTime.now());
    }

    //отмена занятия клиентом
    public boolean cancelByClient(Appointment appointment, LocalDateTime now, String reason) {
        LocalDateTime sessionTime = appointment.getDateTime();

        //если занятие уже прошло - отменять нельзя
        if (sessionTime.isBefore(now)) {
            System.out.println("Нельзя отменить прошедшее занятие.");
            return false;
        }

        long hoursLeft = Duration.between(now, sessionTime).toHours();

        if(hoursLeft < 24) {
            System.out.println("Клиент не может отменить занятие менее чем за 24 часа.");
            return false;
        }

        appointment.cancel(reason);
        System.out.println("Клиент отменил занятие. Причина: " + reason);
        return true;
    }

    //отмена тренером
    public boolean cancelByTrainer(Appointment appointment, String reason) {
        appointment.cancel(reason);
        System.out.println("Тренер отменил занятие. Причина: " +  reason);
        return true;
    }

    //перенос занятия тренером
    public Appointment rescheduleByTrainer(Appointment oldAppointment,
                                           LocalDateTime newDateTime,
                                           LocalDateTime now,
                                           String reason) {

        //1) Нельзя переносить отмененное занятие
        if (oldAppointment.getStatus() == AppointmentStatus.CANCELED) {
            System.out.println("Нельзя переносить отмененное занятие.");
            return null;
        }

        //2)Нельзя переносить в прошлое
        if (newDateTime.isBefore(now)) {
            System.out.println("Нельзя перенести занятие в прошлое.");
            return null;
        }

        //3)Создаем новую запись
        Appointment newAppointment = new Appointment(oldAppointment.getClient(),oldAppointment.getTrainer(),newDateTime);

        //4)Проверяем, что новое время подходит
        boolean booked = createBooking(newAppointment, now);
        if (!booked) {
            System.out.println("Перенос не выполнен: новое время недоступно.");
            return null;
        }

        //5)Отменяем старую запись(фиксируем причину)
        oldAppointment.cancel("Перенос: " + reason);
        System.out.println("Занятие перенесено. Причина: " + reason);
        return newAppointment;
    }
}
