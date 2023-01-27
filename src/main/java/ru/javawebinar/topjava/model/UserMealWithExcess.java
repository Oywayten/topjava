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
    private Excess excess;

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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Excess getExcess() {
        return excess;
    }

    public void setExcess(Excess excess) {
        excess.addCalories(this.excess.sumCalories);
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
        return calories == that.calories && Objects.equals(dateTime, that.dateTime)
               && Objects.equals(description, that.description) && Objects.equals(excess, that.excess);
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

    /**
     * The class implements the logic of detecting a sign of an excess of calories.
     * Created by Oywayten on 22.01.2023.
     */
    public static class Excess {
        /**
         * Calorie limit.
         */
        int limitCalories;
        /**
         * The amount of calories.
         */
        int sumCalories;
        /**
         * Sign of redundancy.
         */
        boolean excess;

        /**
         * The constructor accepts a calorie limit and a start value for the amount of calories.
         * excess is calculated based on the comparison of sumCalories and limitCalories.
         *
         * @param limitCalories calorie limit.
         * @param sumCalories   amount of calories.
         */
        public Excess(int limitCalories, int sumCalories) {
            this.limitCalories = limitCalories;
            this.sumCalories = sumCalories;
            this.excess = sumCalories > limitCalories;
        }

        public Excess(boolean excess) {
            this.excess = excess;
        }

        public boolean isExcess() {
            return excess;
        }

        /**
         * The method adds calories to the sumCalories field using the addition method.
         * After that, the excess field is recalculated.
         *
         * @param calorie calories transferred.
         */
        public void addCalories(int calorie) {
            this.sumCalories += calorie;
            this.excess = sumCalories > limitCalories;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            UserMealWithExcess.Excess excess1 = (UserMealWithExcess.Excess) o;
            return limitCalories == excess1.limitCalories && sumCalories == excess1.sumCalories
                   && excess == excess1.excess;
        }

        @Override
        public int hashCode() {
            return Objects.hash(limitCalories, sumCalories, excess);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", UserMealWithExcess.Excess.class.getSimpleName() + "[", "]")
                    .add("limitCalories=" + limitCalories)
                    .add("sumCalories=" + sumCalories)
                    .add("excess=" + excess)
                    .toString();
        }
    }
}
