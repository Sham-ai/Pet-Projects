package com.kinologapp.model.content;
import com.kinologapp.model.entity.Trainer;

import java.time.LocalDate;
public abstract class Content {
    private Trainer author; //Автор-кинолог
    private String title; // Название
    private LocalDate publishDate; // Дата публикации

    public Content(Trainer author, String title, LocalDate publishDate) {
        this.author = author;
        this.title = title;
        this.publishDate = publishDate;
    }

    //Абстрактный метод: каждый наследник покажет контент по своему
    public abstract void display();

    public Trainer getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
