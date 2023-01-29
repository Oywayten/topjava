package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * To store of processing the food intake record and information about the excess calories per day.
 */
public class UserMealStore {
    /**
     * Date of meal.
     */
    private final LocalDate date;
    /**
     * User meals list.
     */
    private final List<UserMeal> userMeals;
    /**
     * Calorie limit.
     */
    private final int caloriesLimit;
    /**
     * Condition for adding to userMeals.
     */
    private final Predicate<UserMeal> predicate;
    /**
     * The amount of calories received from UserMeal.
     */
    private int caloriesAmount;
    /**
     * A sign of excess food.
     */
    private boolean excess;

    /**
     * Constructor with parameters.
     *
     * @param date          {@link LocalDateTime} date/time of meal.
     * @param caloriesLimit calorie limit.
     * @param predicate     Condition for adding to userMeals.
     */
    public UserMealStore(LocalDate date, int caloriesLimit, Predicate<UserMeal> predicate) {
        this.date = date;
        this.caloriesLimit = caloriesLimit;
        this.predicate = predicate;
        userMeals = new ArrayList<>();
    }

    public boolean isExcess() {
        return excess;
    }

    public List<UserMeal> getUserMeals() {
        return userMeals;
    }

    /**
     * Method for adding userMeal to {@link UserMealStore}, and check condition for add to {@link UserMealStore#userMeals}.
     *
     * @param userMeal user meal.
     * @return {@link UserMealStore} object.
     */
    public UserMealStore addMeal(UserMeal userMeal) {
        caloriesAmount += userMeal.getCalories();
        excess = caloriesAmount > caloriesLimit;
        if (predicate.test(userMeal)) {
            userMeals.add(userMeal);
        }
        return this;
    }

    /**
     * Method for merge two {@link UserMealStore} objects, and check condition for add to {@link UserMealStore#userMeals}.
     *
     * @param store {@link UserMealStore} object.
     * @return {@link UserMealStore} object after merge.
     */
    public UserMealStore merge(UserMealStore store) {
        caloriesAmount += store.caloriesAmount;
        excess = caloriesAmount > caloriesLimit;
        store.userMeals.forEach(userMeal -> {
            if (predicate.test(userMeal)) {
                userMeals.add(userMeal);
            }
        });
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserMealStore that = (UserMealStore) o;
        return excess == that.excess && Objects.equals(date, that.date) && Objects.equals(userMeals, that.userMeals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, userMeals, excess);
    }
}