package com.kinologapp.model.content;

import com.kinologapp.model.entity.User;
import com.kinologapp.model.enums.Role;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Базовый абстрактный класс контента.
 * Автор — кинолог (TRAINER).
 */
public abstract class Content {

    private final User author;
    private final String title;
    private final LocalDate publishDate;

    public Content(User author, String title, LocalDate publishDate) {
        this.author = Objects.requireNonNull(author, "author");
        this.title = Objects.requireNonNull(title, "title");
        this.publishDate = Objects.requireNonNull(publishDate, "publishDate");

        if (!author.hasRole(Role.TRAINER)) {
            throw new IllegalArgumentException("Content author must have TRAINER role");
        }
    }

    public abstract void display();

    public User getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }
}
