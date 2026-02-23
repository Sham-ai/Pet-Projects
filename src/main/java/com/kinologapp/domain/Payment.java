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

        applyDiscount(); //проверка скидок

        this.status = PaymentStatus.SUCCESS;
        this.dateTime = LocalDateTime.now();

        // уведомляем получателя о деньгах(чек)
        System.out.println("--- ЧЕК ОПЛАТЫ ---");
        System.out.println("Тип: " + type);
        System.out.println("Итого к оплате: " + amount);
        receiver.sendNotification("Получена оплата: " + amount);
    }

    public void applyDiscount(){
        if (sender instanceof Client client) { // Pattern Matching
            if (client.isVip()) {
                //скидка 10% (используем Bigdecimal)
                BigDecimal discount = amount.multiply(new BigDecimal("0.10"));
                amount = amount.subtract(discount);
                System.out.println("Применена VIP-скидка! Новая сумма: " + amount);
            }

        }
    }

    // Геттеры (необходимы для доступа к private полям)
    public PaymentStatus getStatus() { return status; }
    public BigDecimal getAmount() { return amount; }

}
