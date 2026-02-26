package com.kinologapp.service;

import com.kinologapp.model.entity.Client;
import com.kinologapp.model.entity.Trainer;
import com.kinologapp.model.payment.Payment;
import com.kinologapp.model.enums.PaymentType;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentServiceTest {
    @Test
    void testVipDiscountCalculation() {
        PaymentService service = new PaymentService();
        Client vipClient = new Client(1,"Vip", "123","DOG-1");
        vipClient.setVip(true);
        Trainer trainer = new Trainer(2,"Trainer","456",5,9,18);

        Payment payment = new Payment(vipClient, trainer, new BigDecimal(1000),PaymentType.SINGLE_LESSON);

        //выполняем действие
        service.processPayment(payment);

        //Проверяем результат (1000 - 10% = 900)
        // Мы используем stripTrailingZeros, чтобы 900.0000 превратилось в 900
        assertEquals(new BigDecimal("900"), payment.getAmount().setScale(2));
    }
}
