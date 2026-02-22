package com.kinologapp.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private User sender; //— кто платит (Клиент или другой Пользователь).
    private User receiver; //— кому платят (Тренер или Клуб).
    private java.math.BigDecimal amount; //— сумма платежа.
    private PaymentType type; //— за что платим.
    private PaymentStatus status; //— текущее состояние.
    private java.time.LocalDateTime dateTime; //— когда прошел платеж

    public Payment(User sender, User receiver, BigDecimal amount, PaymentType type) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.type = type;
        this.status = PaymentStatus.PENDING;
    }

    public void completePayment() {
        this.status = PaymentStatus.SUCCESS;
        this.dateTime = LocalDateTime.now();

        // уведомляем получателя о деньгах
        receiver.sendNotification("Вам поступил платеж на сумму: " + amount);
    }

    // Геттеры (необходимы для доступа к private полям)
    public PaymentStatus getStatus() { return status; }
    public BigDecimal getAmount() { return amount; }

}
