package com.kinologapp.model.payment;

import com.kinologapp.model.enums.PaymentStatus;
import com.kinologapp.model.enums.PaymentType;
import com.kinologapp.model.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private User sender; //— кто платит (Клиент или другой Пользователь).
    private User receiver; //— кому платят (Тренер или Клуб).
    private BigDecimal amount; //— сумма платежа.

    public PaymentType getType() {
        return type;
    }

    private PaymentType type; //— за что платим.
    private PaymentStatus status; //— текущее состояние.
    private LocalDateTime dateTime; //— когда прошел платеж

    public Payment(User sender, User receiver, BigDecimal amount, PaymentType type) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.type = type;
        this.status = PaymentStatus.PENDING;
    }

    // Геттеры
    public PaymentStatus getStatus() { return status; }
    public BigDecimal getAmount() { return amount; }

    // Чтобы сервис мог узнать, кто платит и сколько
    public User getSender() { return sender; }

    // Чтобы сервис мог изменить сумму, статус и время
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public LocalDateTime getDateTime() { return dateTime; }

}
