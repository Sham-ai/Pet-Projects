package com.kinologapp.model.content;

import com.kinologapp.model.entity.Trainer;

import java.time.LocalDate;

public class Article extends Content{

    private String text;
    private boolean hasPhotos;

    public Article(Trainer author, String title, LocalDate publishDate,
                   String text, boolean hasPhotos) {
        super(author, title, publishDate);
        this.hasPhotos = hasPhotos;
        this.text = text;
    }

    @Override
    public void display() {
        System.out.println("Читаем статью: " + getTitle());
        System.out.println("Автор: " + getAuthor().getFirstName());
        //Проверка на наличие фото
        if (hasPhotos) {
            System.out.println("[В статье есть фотоиллюстрации]");
        }
        System.out.println("Текст: "
                + text.substring(0, Math.min(text.length(), 50)) + "...");
    }

}
