package com.kinologapp.domain;

import java.time.LocalDate;

public class VideoLesson extends Content {
    private int duration; //длительность в минутах
    private String videoUrl; //ссылка на видео

    public VideoLesson(Trainer author, String title, LocalDate publishDate, int
                       duration, String videoUrl) {
        super(author, title, publishDate);
        this.duration = duration;
        this.videoUrl = videoUrl;
    }

    @Override
    public void display(){
        System.out.println("Воспроизведение видео: " + getTitle() +
                "[Автор: " + getAuthor().getFirstName() + "]");
        System.out.println("Продолжительность: " + duration + " мин.");
        System.out.println("Ссылка: " + videoUrl);
    }
}
