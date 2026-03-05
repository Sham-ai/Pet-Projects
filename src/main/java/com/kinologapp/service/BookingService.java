package com.kinologapp.service;

import com.kinologapp.model.appointment.Appointment;
import com.kinologapp.model.entity.TrainerProfile;
import com.kinologapp.model.enums.AppointmentStatus;

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
}
