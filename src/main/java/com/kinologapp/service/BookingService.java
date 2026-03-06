package com.kinologapp.service;

import com.kinologapp.model.appointment.Appointment;
import com.kinologapp.model.appointment.RescheduleRequest;
import com.kinologapp.model.entity.TrainerProfile;
import com.kinologapp.model.entity.User;
import com.kinologapp.model.enums.AppointmentStatus;
import com.kinologapp.model.enums.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;

public class BookingService {

    private static final Logger log =  LoggerFactory.getLogger(BookingService.class);

    private long requestIdSeq = 1L;

    //создание заявки клиентом
    public RescheduleRequest createRescheduleRequestByClient(Appointment appointment,
                                                             User client,
                                                             LocalDateTime desiredDateTime,
                                                             LocalDateTime now,
                                                             String comment) {
        // клиент не переносит сам — только просит,
        // но у него уже есть правило 24 часа на отмену;
        // для заявки оставляем то же правило
        long hoursLeft = java.time.Duration.between(now, appointment.getDateTime()).toHours();
        if (hoursLeft < 24) {
            log.warn("Клиент не может запрашивать перенос менее чем за 24 часа до занятия.");
            return null;
        }

        return new RescheduleRequest(requestIdSeq++,
                appointment,
                client,
                desiredDateTime,
                comment,
                now
        );
    }

    //одобрение заявки тренером
    public Appointment approveRescheduleRequestByTrainer(RescheduleRequest request,
                                                         User trainer,
                                                         LocalDateTime now,
                                                         String decisionComment) {
        if (!trainer.hasRole(Role.TRAINER)){
            throw new IllegalArgumentException("Only TRAINER can approve requests");
        }
        if (request.getAppointment().getTrainer().getId() != trainer.getId()){
            throw new IllegalArgumentException("Trainer can approve only own appointments");
        }

        Appointment newAppointment = rescheduleByTrainer(
                request.getAppointment(),
                request.getDesiredDateTime(),
                now,
                "Approved request #" + request.getId()
        );

        if (newAppointment == null){
            //если перенос не удался (вне графика/прошлое/и т.д.)
            request.reject("Cannot reschedule: " + decisionComment);
            return null;
        }

        request.approve(decisionComment);
        return newAppointment;
    }

    //Тренер отклоняет заявку
    public void rejectRescheduleRequestByTrainer(RescheduleRequest request,
                                                 User trainer,
                                                 String decisionComment) {
        if (!trainer.hasRole(Role.TRAINER)){
            throw new IllegalArgumentException("Only TRAINER can reject requests");
        }
        if (request.getAppointment().getTrainer().getId() != trainer.getId()){
            throw new IllegalArgumentException("Trainer can reject only own appointments");
        }
        request.reject(decisionComment);
    }

    //логика бронирования
    public boolean createBooking(Appointment appointment, LocalDateTime now) {
        LocalDateTime sessionTime = appointment.getDateTime();

        if (sessionTime.isBefore(now)) {
            log.warn("Ошибка бронирования: Нельзя осуществить запись задним числом!");
            return false;
        }

        TrainerProfile trainerProfile = appointment.getTrainer().getTrainerProfile();
        if (trainerProfile == null) {
            log.warn("Ошибка: у выбранного тренера не заполнен TrainerProfile (рабочие часы неизвестны)");
            return false;
        }

        int hour = sessionTime.getHour();
        int start = trainerProfile.getStartWorkHour();
        int end = trainerProfile.getEndWorkHour();

        if (hour < start || hour >= end) {
            log.warn("Ошибка: Тренер работает только с " + start + ":00 до " + end + ":00");
            return false;
        }

        appointment.setStatus(AppointmentStatus.SCHEDULED);
        log.warn("Запись подтверждена на " + sessionTime);
        return true;
    }

    public void createBooking(Appointment appointment) {
        createBooking(appointment, LocalDateTime.now());
    }

    //отмена занятия клиентом
    public boolean cancelByClient(Appointment appointment, LocalDateTime now, String reason) {
        LocalDateTime sessionTime = appointment.getDateTime();

        //если занятие уже прошло - отменять нельзя
        if (sessionTime.isBefore(now)) {
            log.warn("Нельзя отменить прошедшее занятие.");
            return false;
        }

        long hoursLeft = Duration.between(now, sessionTime).toHours();

        if(hoursLeft < 24) {
            log.warn("Клиент не может отменить занятие менее чем за 24 часа.");
            return false;
        }

        appointment.cancel(reason);
        log.warn("Клиент отменил занятие. Причина: " + reason);
        return true;
    }

    //отмена тренером
    public boolean cancelByTrainer(Appointment appointment, String reason) {
        appointment.cancel(reason);
        log.warn("Тренер отменил занятие. Причина: " +  reason);
        return true;
    }

    //перенос занятия тренером
    public Appointment rescheduleByTrainer(Appointment oldAppointment,
                                           LocalDateTime newDateTime,
                                           LocalDateTime now,
                                           String reason) {

        //1) Нельзя переносить отмененное занятие
        if (oldAppointment.getStatus() == AppointmentStatus.CANCELED) {
            log.warn("Нельзя переносить отмененное занятие.");
            return null;
        }

        //2)Нельзя переносить в прошлое
        if (newDateTime.isBefore(now)) {
            log.warn("Нельзя перенести занятие в прошлое.");
            return null;
        }

        //3)Создаем новую запись
        Appointment newAppointment = new Appointment(oldAppointment.getClient(),oldAppointment.getTrainer(),newDateTime);

        //4)Проверяем, что новое время подходит
        boolean booked = createBooking(newAppointment, now);
        if (!booked) {
            log.warn("Перенос не выполнен: новое время недоступно.");
            return null;
        }

        //5)Отменяем старую запись(фиксируем причину)
        oldAppointment.cancel("Перенос: " + reason);
        log.warn("Занятие перенесено. Причина: " + reason);
        return newAppointment;
    }
}
