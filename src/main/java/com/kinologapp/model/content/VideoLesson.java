package com.kinologapp.model.content;

import com.kinologapp.model.entity.User;

import java.time.LocalDate;

public class VideoLesson extends Content {

    private final int duration;
    private final String videoUrl;

    public VideoLesson(User author, String title, LocalDate publishDate,
                       int duration, String videoUrl) {
        super(author, title, publishDate);
        this.duration = duration;
        this.videoUrl = videoUrl;
    }

    @Override
    public void display() {
        System.out.println("Воспроизведение видео: " + getTitle() +
                " [Автор: " + getAuthor().getFirstName() + "]");
        System.out.println("Продолжительность: " + duration + " мин.");
        System.out.println("Ссылка: " + videoUrl);
    }
}
