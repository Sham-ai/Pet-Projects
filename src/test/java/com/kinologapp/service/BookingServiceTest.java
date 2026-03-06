package com.kinologapp.service;

import com.kinologapp.model.appointment.Appointment;
import com.kinologapp.model.entity.ClientProfile;
import com.kinologapp.model.entity.TrainerProfile;
import com.kinologapp.model.entity.User;
import com.kinologapp.model.enums.AppointmentStatus;
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

    // Успешный перенос на подходящее время
    @Test
    void trainerCanRescheduleToValidTime() {
        BookingService service = new BookingService();

        User trainer = new User(2, "Trainer", "456");
        trainer.enableTrainer(new TrainerProfile(5, 9, 18));

        User client = new User(1, "Client", "123");
        client.enableClient(new ClientProfile(false));

        LocalDateTime now = LocalDateTime.of(2026, 3, 5, 10, 0);

        LocalDateTime original = now.plusDays(2).withHour(10).withMinute(0);
        Appointment oldAppt = new Appointment(client, trainer, original);
        assertTrue(service.createBooking(oldAppt, now));

        LocalDateTime newTime = now.plusDays(3).withHour(11).withMinute(0);
        Appointment newAppt = service.rescheduleByTrainer(oldAppt, newTime, now, "Клиент попросил другое время.");

        assertNotNull(newAppt);
        assertEquals(AppointmentStatus.CANCELED, oldAppt.getStatus());
        assertTrue(oldAppt.getCancelReason().startsWith("Перенос: "));
        assertEquals(AppointmentStatus.SCHEDULED, newAppt.getStatus());
        assertEquals(newTime, newAppt.getDateTime());
    }

    //Нельзя перенести на время вне рабочих часов
    @Test
    void trainerCannotRescheduleOutsideWorkHours() {
        BookingService service = new BookingService();

        User trainer = new User(2, "Trainer", "456");
        trainer.enableTrainer(new TrainerProfile(5, 9, 18));

        User client = new User(1, "Client", "123");
        client.enableClient(new ClientProfile(false));

        LocalDateTime now = LocalDateTime.of(2026, 3, 5, 10, 0);

        LocalDateTime original = now.plusDays(2).withHour(10).withMinute(0);
        Appointment oldAppt = new Appointment(client, trainer, original);
        assertTrue(service.createBooking(oldAppt, now));

        // 20:00 вне графика
        LocalDateTime badTime = now.plusDays(3).withHour(20).withMinute(0);
        Appointment newAppt = service.rescheduleByTrainer(oldAppt, badTime, now, "Хочу вечером");

        // перенос должен НЕ удаться
        assertNull(newAppt);
        // старое занятие остаётся активным
        assertEquals(AppointmentStatus.SCHEDULED, oldAppt.getStatus());
    }

    // Нельзя перенести отменённое занятие
    @Test
    void trainerCannotRescheduleCanceledAppointment() {
        BookingService service = new BookingService();

        User trainer = new User(2, "Trainer", "456");
        trainer.enableTrainer(new TrainerProfile(5, 9, 18));

        User client = new User(1, "Client", "123");
        client.enableClient(new ClientProfile(false));

        LocalDateTime now = LocalDateTime.of(2026, 3, 5, 10, 0);

        LocalDateTime original = now.plusDays(2).withHour(10).withMinute(0);
        Appointment oldAppt = new Appointment(client, trainer, original);
        assertTrue(service.createBooking(oldAppt, now));

        // отменили
        service.cancelByTrainer(oldAppt, "Отмена");

        LocalDateTime newTime = now.plusDays(3).withHour(11).withMinute(0);
        Appointment newAppt = service.rescheduleByTrainer(oldAppt, newTime, now, "Попробуем перенести");

        // перенос должен НЕ удаться
        assertNull(newAppt);
    }
}


