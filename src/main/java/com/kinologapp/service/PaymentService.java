package com.kinologapp.service;

import com.kinologapp.model.entity.ClientProfile;
import com.kinologapp.model.enums.PaymentStatus;
import com.kinologapp.model.payment.Payment;
import com.kinologapp.repository.DataStorage;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {

    public void processPayment(Payment payment) {
        applyVipDiscount(payment);

        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setDateTime(LocalDateTime.now());

        DataStorage.addPayment(payment);

        System.out.println("PaymentService: Оплата обработана и сохранена в базу.");
    }

    private void applyVipDiscount(Payment payment) {
        ClientProfile clientProfile = payment.getSender().getClientProfile();

        if (clientProfile != null && clientProfile.isVip()) {
            // BigDecimal — из строки, не из double
            BigDecimal discountRate = new BigDecimal("0.10");
            BigDecimal discount = payment.getAmount().multiply(discountRate);
            payment.setAmount(payment.getAmount().subtract(discount));

            System.out.println("PaymentService: Применена скидка 10% для VIP-клиента.");
        }
    }
}
