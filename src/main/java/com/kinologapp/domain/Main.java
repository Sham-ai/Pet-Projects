package com.kinologapp.domain;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // 1. Создаем тренера
        Trainer alex = new Trainer(1, "Алексей", "8900...", 10);

        // 2. Создаем видео-урок (с исправленным конструктором)
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