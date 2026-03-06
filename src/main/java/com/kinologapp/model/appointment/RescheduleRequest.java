package com.kinologapp.model.appointment;

import com.kinologapp.model.entity.User;
import com.kinologapp.model.enums.RescheduleRequestStatus;
import com.kinologapp.model.enums.Role;

import java.time.LocalDateTime;
import java.util.Objects;

public class RescheduleRequest {

    private final long id;
    private final Appointment appointment;
    private final User requestedBy; //кто просил(клиент)
    private final LocalDateTime desiredDateTime; //желаемое время
    private final String comment;
    private final LocalDateTime createdAt;

    private RescheduleRequestStatus status =  RescheduleRequestStatus.PENDING;
    private String decisionComment; //причина отказа/подтверждения

    public RescheduleRequest(long id,
                             Appointment appointment,
                             User requestedBy,
                             LocalDateTime desiredDateTime,
                             String comment,
                             LocalDateTime createdAt) {
        this.id = id;
        this.appointment = Objects.requireNonNull(appointment, "appointment");
        this.requestedBy = Objects.requireNonNull(requestedBy, "requestedBy");
        this.desiredDateTime = Objects.requireNonNull(desiredDateTime, "desiredDateTime");
        this.comment = comment;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");

        //доменные защиты
        if (!requestedBy.hasRole(Role.CLIENT)) {
            throw  new IllegalArgumentException("Only CLIENT can create reschedule request");
        }
        if (appointment.getClient().getId() != requestedBy.getId()) {
            throw  new IllegalArgumentException("Client can request reschedule only for own appointment");
        }
        if (desiredDateTime.isBefore(createdAt)) {
            throw  new IllegalArgumentException("desiredDateTime cannot be in the past");
        }
    }

    public long getId() {
        return id;
    }
    public Appointment getAppointment() {
        return appointment;
    }
    public User getRequestedBy() {
        return requestedBy;
    }
    public LocalDateTime getDesiredDateTime() {
        return desiredDateTime;
    }
    public String getComment() {
        return comment;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public RescheduleRequestStatus getStatus() {
        return status;
    }

    public String getDecisionComment() {
        return decisionComment;
    }
    public void approve(String decisionComment) {
        ensurePending();
        this.decisionComment = decisionComment;
        this.status = RescheduleRequestStatus.APPROVED;
    }

    public void reject(String decisionComment) {
        ensurePending();
        this.decisionComment = decisionComment;
        this.status = RescheduleRequestStatus.REJECTED;
    }

    private void ensurePending() {
        if (status != RescheduleRequestStatus.PENDING) {
            throw  new IllegalArgumentException("Request already decided: " + status);
        }
    }
}
