package com.kinologapp.service;

import com.kinologapp.model.entity.ClientProfile;
import com.kinologapp.model.entity.TrainerProfile;
import com.kinologapp.model.entity.User;
import com.kinologapp.model.enums.PaymentType;
import com.kinologapp.model.payment.Payment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentServiceTest {

    @Test
    void testVipDiscountCalculation() {
        PaymentService service = new PaymentService();

        User vipClient = new User(1, "Vip", "123");
        vipClient.enableClient(new ClientProfile(true));

        User trainer = new User(2, "Trainer", "456");
        trainer.enableTrainer(new TrainerProfile(5, 9, 18));

        Payment payment = new Payment(vipClient, trainer, new BigDecimal("1000.00"), PaymentType.SINGLE_LESSON);

        service.processPayment(payment);

        BigDecimal actual = payment.getAmount().setScale(2, RoundingMode.HALF_UP);

        // BigDecimal: для денег сравниваем через compareTo (игнорирует scale)
        assertEquals(0, new BigDecimal("900.00").compareTo(actual));
    }
}