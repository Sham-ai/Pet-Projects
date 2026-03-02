package com.kinologapp.service;

import com.kinologapp.model.enums.PaymentType;
import com.kinologapp.model.payment.Payment;
import com.kinologapp.repository.DataStorage;

import java.math.BigDecimal;
import java.util.List;

public class StatisticsService {

    public static BigDecimal calculateTotalRevenue(PaymentType targetType) {
        BigDecimal total = BigDecimal.ZERO;
        List<Payment> allPayments = DataStorage.getAllPayments();

        for (Payment p : allPayments) {
            if(p.getType() == targetType) {
                total = total.add(p.getAmount());
            }
        }
        return total;
    }

    public static void printFullReport() {
        System.out.println("--- ПОЛНЫЙ ОТЧЕТ ПО ВЫРУЧКЕ ---");

        //пробегаем по всем значениям PaymentType
        for (PaymentType type : PaymentType.values()) {
            BigDecimal total = calculateTotalRevenue(type);

            if(total.compareTo(BigDecimal.ZERO) > 0) {
                System.out.println(type + ": " + total + " руб.");
            }
        }
    }
}
