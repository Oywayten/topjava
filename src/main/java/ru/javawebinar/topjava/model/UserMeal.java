package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.StringJoiner;

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

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserMeal.class.getSimpleName() + "[", "]")
                .add("dateTime=" + dateTime)
                .add("description='" + description + "'")
                .add("calories=" + calories)
                .toString();
    }
}
