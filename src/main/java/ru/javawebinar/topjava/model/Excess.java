package ru.javawebinar.topjava.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * The class implements the logic of detecting a sign of an excess of calories.
 * Created by Oywayten on 22.01.2023.
 */
public class Excess {
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
        Excess excess1 = (Excess) o;
        return limitCalories == excess1.limitCalories && sumCalories == excess1.sumCalories
               && excess == excess1.excess;
    }

    @Override
    public int hashCode() {
        return Objects.hash(limitCalories, sumCalories, excess);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Excess.class.getSimpleName() + "[", "]")
                .add("limitCalories=" + limitCalories)
                .add("sumCalories=" + sumCalories)
                .add("excess=" + excess)
                .toString();
    }
}
