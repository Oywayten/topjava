package ru.javawebinar.topjava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class UserMealStore {
    private final List<UserMeal> userMeals;
    private final int caloriesLimit;
    private final Predicate<UserMeal> predicate;
    private int caloriesAmount;
    private boolean excess;

    public UserMealStore(UserMeal meal, int caloriesLimit, Predicate<UserMeal> predicate) {
        this.caloriesLimit = caloriesLimit;
        caloriesAmount += meal.getCalories();
        excess = caloriesAmount > caloriesLimit;
        this.predicate = predicate;
        userMeals = new ArrayList<>();
        if (predicate.test(meal)) {
            userMeals.add(meal);
        }
    }

    public boolean isExcess() {
        return excess;
    }

    public List<UserMeal> getUserMeals() {
        return userMeals;
    }

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
}