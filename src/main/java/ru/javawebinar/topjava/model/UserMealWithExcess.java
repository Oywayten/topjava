package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The result of processing the food intake record and information about the excess calories per day.
 */
public class UserMealWithExcess {
    /**
     * Date and time of meal.
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
     * Food redundancy instance.
     */
    private final Excess excess;

    /**
     * Constructor for creating a meal with a sign of excess. Accepts datetime, description, calories,
     * and a sign of excess. Used in the first few tests.
     *
     * @param dateTime    {@link LocalDateTime} date/time of meal.
     * @param description {@link String} food description.
     * @param calories    int calorie content of food.
     * @param excess      boolean sign of overeating: true if the amount of calories for that day exceeds the set
     *                    calorie limit; false if not greater.
     */
    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = new Excess(excess);
    }

    /**
     * Constructor for creating a meal with a sign of excess.
     * Accepts datetime, description and calories. Designed for ease of testing.
     *
     * @param dateTime    {@link LocalDateTime} date/time of meal.
     * @param description {@link String} food description.
     * @param calories    int calorie content of food.
     * @param excess      {@link Excess} sign of excess food.
     */
    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, Excess excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserMealWithExcess that = (UserMealWithExcess) o;
        return calories == that.calories && dateTime.equals(that.dateTime)
               && description.equals(that.description) && excess.equals(that.excess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, description, calories, excess);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserMealWithExcess.class.getSimpleName() + "[", "]")
                .add("dateTime=" + dateTime)
                .add("description='" + description + "'")
                .add("calories=" + calories)
                .add("excess=" + excess)
                .toString();
    }
}
