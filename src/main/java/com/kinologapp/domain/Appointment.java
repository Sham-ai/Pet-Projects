package com.kinologapp.domain;
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
