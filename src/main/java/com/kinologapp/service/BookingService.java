package com.kinologapp.service;

import com.kinologapp.model.appointment.Appointment;
import com.kinologapp.model.enums.AppointmentStatus;
import java.time.LocalDateTime;

public class BookingService {
    /**
     * пытается создать запись на тренировку
     * возвращает true, если запись возможна, и false, если есть ошибки.
     */
    public boolean createBooking(Appointment appointment) {
        LocalDateTime sessionTime = appointment.getDateTime();

        //проверка на запись задним числом
        if (sessionTime.isBefore(LocalDateTime.now())) {
            System.out.println("Ошибка бронирования: Нельзя осуществить запись задним числом!");
            return false;
        }

        //проверка на рабочий график
        int hour = sessionTime.getHour();
        int start = appointment.getTrainer().getStartWorkHour();
        int end = appointment.getTrainer().getEndWorkHour();

        if (hour < start || hour >= end) {
            System.out.println("Ошибка: Тренер работает только с " +
                    + start + ":00 до " + end + ":00");
            return false;
        }

        //если все ок - подтверждаем запись
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        System.out.println("Запись подтверждена на " + sessionTime);
        return true;
    }

}
