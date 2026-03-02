package com.kinologapp.service;

import com.kinologapp.model.payment.Payment;
import com.kinologapp.model.entity.Client;
import com.kinologapp.model.enums.PaymentStatus;
import com.kinologapp.repository.DataStorage;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {

    public static void processPayment(Payment payment) {
        //применяем скидку
        applyVipDiscount(payment);

        //устанавливаем статус и время
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setDateTime(LocalDateTime.now());

        //сохраняем в "базу"
        DataStorage.addPayment(payment);

        System.out.println("PaymentService: Оплата обработана и сохранена в базу.");
    }

    private static void applyVipDiscount(Payment payment) {
        if(payment.getSender() instanceof Client client && client.isVip()) {
            BigDecimal discount = payment.getAmount().multiply(new BigDecimal(0.10));
            payment.setAmount(payment.getAmount().subtract(discount));
            System.out.println("PaymentService: Применена скидка 10% для VIP-клиента.");
        }
    }
}
