package ru.javawebinar.topjava.model;

import java.text.MessageFormat;
import java.time.LocalDateTime;

public class MealTo {

    private final int id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public MealTo(int id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
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

    public boolean isExcess() {
        return excess;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return MessageFormat.format("MealTo'{'dateTime={0}, description=''{1}'', calories={2}, excess={3}'}'",
                dateTime, description, calories, excess);
    }
}
