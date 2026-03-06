package com.kinologapp.model.appointment;

import com.kinologapp.model.entity.User;
import com.kinologapp.model.enums.AppointmentStatus;
import com.kinologapp.model.enums.Role;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Appointment — запись на занятие.
 * Trainer/Client — это роли одного User.
 */
public class Appointment {

    private final User trainer;
    private final User client;
    private final LocalDateTime dateTime;

    private AppointmentStatus status;
    private String cancelReason;

    private boolean silent = false;

    public Appointment(User client, User trainer, LocalDateTime dateTime) {
        this.client = Objects.requireNonNull(client, "client");
        this.trainer = Objects.requireNonNull(trainer, "trainer");
        this.dateTime = Objects.requireNonNull(dateTime, "dateTime");
        this.status = AppointmentStatus.PENDING;

        // доменная защита
        if (!trainer.hasRole(Role.TRAINER)) {
            throw new IllegalArgumentException("trainer must have TRAINER role");
        }
        if (!client.hasRole(Role.CLIENT)) {
            throw new IllegalArgumentException("client must have CLIENT role");
        }
    }

    public User getTrainer() {
        return trainer;
    }

    public User getClient() {
        return client;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    // на консольном этапе уведомления оставляем тут
    public void cancel(String reason) {
        this.cancelReason = reason;
        setStatus(AppointmentStatus.CANCELED);
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;

        if (status == AppointmentStatus.CANCELED && !silent) {
            System.out.println("Внимание! Занятие отменено. Причина: " + cancelReason);
            trainer.sendNotification("Занятие отменено! Причина: " + cancelReason);
            client.sendNotification("Ваше занятие отменено. Причина: " + cancelReason);
        }
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }
}
