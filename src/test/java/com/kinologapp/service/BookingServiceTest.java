package com.kinologapp.service;

import com.kinologapp.model.appointment.Appointment;
import com.kinologapp.model.entity.ClientProfile;
import com.kinologapp.model.entity.TrainerProfile;
import com.kinologapp.model.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    @Test
    void clientCanCancelMoreThan24HoursBefore(){
        BookingService service = new BookingService();

        User trainer = new User(2,"Trainer", "456");
        trainer.enableTrainer(new TrainerProfile(5,9,18));

        User client =  new User(1,"Client", "123");
        client.enableClient(new ClientProfile(false));

        LocalDateTime now = LocalDateTime.of(2026,3,5,10,0);
        LocalDateTime session = now.plusHours(30);

        Appointment appointment = new Appointment(client,trainer,session);
        boolean ok = service.cancelByClient(appointment, now, "Не могу прийти");

        assertTrue(ok);
        assertEquals("Не могу прийти", appointment.getCancelReason());
    }

    @Test
    void clientCannotCancelLessThan24HoursBefore(){
        BookingService service = new BookingService();

        User trainer = new User(2,"Trainer", "456");
        trainer.enableTrainer(new TrainerProfile(5,9,18));

        User client =  new User(1,"Client", "123");
        client.enableClient(new ClientProfile(false));

        LocalDateTime now = LocalDateTime.of(2026,3,5,10,0);
        LocalDateTime session = now.plusHours(10);

        Appointment appointment = new Appointment(client,trainer,session);

        boolean ok = service.cancelByClient(appointment, now, "Передумал");

        assertFalse(ok);
        assertNull(appointment.getCancelReason());
    }

    @Test
    void trainerCanCancelAnytime(){
        BookingService service = new BookingService();

        User trainer = new User(2,"Trainer", "456");
        trainer.enableTrainer(new TrainerProfile(5,9,18));

        User client =  new User(1,"Client", "123");
        client.enableClient(new ClientProfile(false));

        LocalDateTime now = LocalDateTime.of(2026,3,5,10,0);
        LocalDateTime session = now.plusHours(1);

        Appointment appointment = new Appointment(client,trainer,session);

        boolean ok = service.cancelByTrainer(appointment, "Заболел");

        assertTrue(ok);
        assertEquals("Заболел", appointment.getCancelReason());
    }
}


