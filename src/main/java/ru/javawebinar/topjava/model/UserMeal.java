package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

/**
 * Модель данных приема еды.
 */
public class UserMeal {
    /**
     * Дата и время приема еды.
     */
    private final LocalDateTime dateTime;
    /**
     * Описание еды.
     */
    private final String description;
    /**
     * Калорийность еды.
     */
    private final int calories;

    /**
     * Конструктор для создания приема еды. Принимает дата/время, описание и
     * калории.
     *
     * @param dateTime    {@link LocalDateTime} дата/время приема еды.
     * @param description {@link String} описание еды.
     * @param calories    int калорийность еды.
     */
    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }
}
