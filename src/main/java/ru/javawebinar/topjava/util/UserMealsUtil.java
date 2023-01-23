package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Утилитный класс по питанию.
 */
public class UserMealsUtil {
    /**
     * Метод для простой проверки результата работы класса.
     *
     * @param args аргументы метода (не используются).
     */
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000));
    }

    /**
     * Utility method converts UserMeal to UserMealWithExcess.
     */
    private static UserMealWithExcess getUserMealWithExcess(UserMeal userMeal, boolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }

    /**
     * HW0.
     * The method gets a list of meals, start and end times between 00:00 and 23:59, daily calorie limit, to get a list
     * of time-filtered food entries.
     * The method is implemented on cycles.
     *
     * @param meals          food list {@link UserMeal}.
     * @param startTime      start of daily time slot.
     * @param endTime        end of daily time slot.
     * @param caloriesPerDay int maximum calories per day.
     * @return {@link UserMealWithExcess} a list of entries with a calorie surplus flag.
     */
    public static List<UserMealWithExcess> filteredByCycles(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateCaloriesMap = new HashMap<>();
        for (UserMeal meal : meals) {
            dateCaloriesMap.merge(meal.getDate(), meal.getCalories(), Integer::sum);
        }
        List<UserMealWithExcess> result = new ArrayList<>();
        for (UserMeal meal : meals) {
            final boolean excess = dateCaloriesMap.get(meal.getDate()) > caloriesPerDay;
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                result.add(getUserMealWithExcess(meal, excess));
            }
        }
        return result;
    }

    /**
     * Optional with Stream API.
     * The method gets a list of meals, start and end times between 00:00 and 23:59, daily calorie limit, to get a list
     * of time-filtered food entries.
     * The method is implemented on streams.
     *
     * @param meals          food list {@link UserMeal}
     * @param startTime      start of daily time slot.
     * @param endTime        end of daily time slot.
     * @param caloriesPerDay int maximum calories per day.
     * @return {@link UserMealWithExcess} a list of entries with a calorie surplus flag.
     */
    public static List<UserMealWithExcess> filteredByStreams(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final Map<LocalDate, Integer> dateCaloriesMap = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream().filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getTime(), startTime, endTime))
                .map(userMeal -> getUserMealWithExcess(userMeal, dateCaloriesMap.get(userMeal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
