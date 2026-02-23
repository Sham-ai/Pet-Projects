package com.kinologapp.domain;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Создаем тренера и клиента
        Trainer alex = new Trainer(1, "Алексей", "8900...", 10);
        Client ivan = new Client(2,"Иван","8911...", "DOG-123");

        //Делаем Ивана VIP-клиентом
        ivan.setVip(true);

        // Создаем платеж за пакет занятий (например, 5000.00)
        Payment packagePayment = new Payment(ivan,alex,
                new java.math.BigDecimal(5000.00),PaymentType.PACKAGE);

        System.out.println("--- Начало транзакции ---");

        // Запускаем проведение оплаты
        // Внутри этого метода сработает applyDiscounts() и сумма уменьшится
        packagePayment.completePayment();

        System.out.println("--- Транзакция завершена ---");

        // Создаем видео-урок (с исправленным конструктором)
        VideoLesson video = new VideoLesson(
                alex,
                "Как научить собаку команде Рядом",
                LocalDate.now(),
                15,
                "youtube.com/lesson1"
        );

        // 3. Создаем статью
        Article article = new Article(
                alex,
                "Питание щенка",
                LocalDate.now(),
                "Очень длинный текст о том, чем кормить маленького лабрадора...",
                true
        );

        // 4. Кладем всё в общий список Контента (Полиморфизм!)
        java.util.List<Content> library = new java.util.ArrayList<>();
        library.add(video);
        library.add(article);

        // 5. Выводим всё на экран одной командой
        System.out.println("--- БИБЛИОТЕКА КИНОЛОГА ---");
        for (Content c : library) {
            c.display();
            System.out.println("---------------------------");
        }
    }
}