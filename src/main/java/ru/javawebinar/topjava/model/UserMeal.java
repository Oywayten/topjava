package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.StringJoiner;

/**
 * Meal data model.
 */
public class UserMeal {
    /**
     * Date and time of the meal.
     */
    private final LocalDateTime dateTime;
    /**
     * Description of food.
     */
    private final String description;
    /**
     * Calorie food.
     */
    private final int calories;

    /**
     * Constructor for creating a meal.
     * Accepts datetime, description and calories.
     *
     * @param dateTime    {@link LocalDateTime} datetime of the meal.
     * @param description {@link String} food description.
     * @param calories    int calorie food.
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
