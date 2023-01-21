package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Результат обработки записи приема еды и информация о превышении калорий за день.
 */
public class UserMealWithExcess {
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
     * Признак избыточности еды.
     */
    private boolean excess;

    /**
     * Конструктор для создания приема еды с признаком избытка. Принимает дата/время, описание и
     * калории.
     *
     * @param dateTime    {@link LocalDateTime} дата/время приема еды.
     * @param description {@link String} описание еды.
     * @param calories    int калорийность еды.
     * @param excess      boolean признак избыточности еды: true, если сумма калорий в этот день
     *                    превышает установленный предел calories; false, если
     *                    не превышает.
     */
    public UserMealWithExcess(LocalDateTime dateTime, String description,
                              int calories, boolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    /**
     * Конструктор для создания приема еды с признаком избытка. Принимает {@link UserMeal} и
     * boolean признак избыточности еды true или false.
     *
     * @param userMeal прием пищи {@link UserMeal}.
     * @param excess   boolean признак избыточности еды: true, если сумма калорий в этот день
     *                 превышает установленный предел; false, если
     *                 не превышает.
     */
    public UserMealWithExcess(UserMeal userMeal, boolean excess) {
        this.dateTime = userMeal.getDateTime();
        this.description = userMeal.getDescription();
        this.calories = userMeal.getCalories();
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

    public void setExcess(boolean excess) {
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
        return calories == that.calories && excess == that.excess
               && dateTime.equals(that.dateTime)
               && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, description, calories, excess);
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{"
               + "dateTime=" + dateTime
               + ", description='" + description + '\''
               + ", calories=" + calories
               + ", excess=" + excess + '}';
    }
}
