package com.kinologapp.model.appointment;
import com.kinologapp.model.enums.AppointmentStatus;
import com.kinologapp.model.entity.Client;
import com.kinologapp.model.entity.Trainer;

import java.time.LocalDateTime;

public class Appointment {
    private Trainer trainer; //— кто проводит занятие.
    private Client client; //— кто придет.
    private LocalDateTime dateTime; //— дата и время начала (с точностью до часа ).
    private AppointmentStatus status; //— статус из нашего Enum.
    private String cancelReason; //— причина отмены (заполняется, только если статус CANCELED)

    public Appointment(Trainer trainer, Client client, String cancelReason, AppointmentStatus status, LocalDateTime dateTime) {
        this.trainer = trainer;
        this.client = client;
        this.cancelReason = cancelReason;
        this.status = status;
        this.dateTime = dateTime;
    }

    // Удобный конструктор для новых записей
    public Appointment(Client client, Trainer trainer, LocalDateTime dateTime) {
        this.client = client;
        this.trainer = trainer;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.PENDING; // Статус по умолчанию
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public boolean confirmBooking() {
        if(dateTime.isBefore(LocalDateTime.now())) {
            System.out.println("Ошибка: Нельзя записаться на прошедшее время!");
            return false;
        }

        int hour = dateTime.getHour();
        //проверка на диапазон записи
        if(hour < trainer.getStartWorkHour() || hour
                >= trainer.getEndWorkHour()) {
            System.out.println("Ошибка! Тренер отдыхает в " + hour + ":00. Запишитесь с "
                    + trainer.getStartWorkHour() + " до " + trainer.getEndWorkHour());
            return false;
        }

        this.status = AppointmentStatus.SCHEDULED;
        System.out.println("Запись подтверждена на " + dateTime);
        return true;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
        if (status == AppointmentStatus.CANCELED) {
            System.out.println("Внимание! Занятие отменено. Причина: " + cancelReason);

            // Магия интерфейса: уведомляем и тренера, и клиента
            trainer.sendNotification("Занятие отменено! Причина: " + cancelReason);
            client.sendNotification("Ваше занятие отменено. Причина: " + cancelReason);
        }
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
