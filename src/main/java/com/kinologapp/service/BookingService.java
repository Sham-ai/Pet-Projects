package com.kinologapp.service;

import com.kinologapp.model.appointment.Appointment;
import com.kinologapp.model.entity.TrainerProfile;
import com.kinologapp.model.enums.AppointmentStatus;
import org.w3c.dom.ls.LSOutput;

import java.time.Duration;
import java.time.LocalDateTime;

public class BookingService {

    public boolean createBooking(Appointment appointment) {
        LocalDateTime sessionTime = appointment.getDateTime();

        if (sessionTime.isBefore(LocalDateTime.now())) {
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
}
