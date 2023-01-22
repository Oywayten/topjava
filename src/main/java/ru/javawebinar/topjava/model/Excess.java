package ru.javawebinar.topjava.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Класс реализует логику выявления признака избытка калорий.
 * Created by Oywayten on 22.01.2023.
 */
public class Excess {
    /**
     * Лимит калорий.
     */
    int limitCalories;
    /**
     * Сумма калорий.
     */
    int sumCalories;
    /**
     * Признак избыточности.
     */
    boolean excess;

    /**
     * Конструктор принимает лимит калорий и стартовое значение суммы калорий.
     * excess высчитывается на основе сравненения sumCalories и limitCalories.
     *
     * @param limitCalories лимит калорий.
     * @param sumCalories   сумма калорий.
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
     * Метод добавляет калории к полю sumCalories методом сложения. После этого пересчитывает
     * поле excess.
     *
     * @param calorie переданные калории.
     */
    public void addCalories(int calorie) {
        this.sumCalories += calorie;
        this.excess = sumCalories > limitCalories;
    }

    public boolean isExcess() {
        return excess;
    }

    public void setExcess(boolean excess) {
        this.excess = excess;
    }

    public int getSumCalories() {
        return sumCalories;
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
