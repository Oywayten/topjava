package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)
        );

    }

    public static List<UserMealWithExcess> filteredByCycles(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMeal> tmp = new ArrayList<>();
        List<UserMealWithExcess> result = new ArrayList<>();
        int calSum = 0;
        for (UserMeal meal : meals) {
            if (meal.getDateTime().toLocalTime().isAfter(startTime)
                    && meal.getDateTime().toLocalTime().isBefore(endTime)) {
                tmp.add(meal);
                calSum += meal.getCalories();
            }
        }
        boolean excess = calSum > caloriesPerDay;
        for (UserMeal meal : tmp) {
            result.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess));
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Stream<UserMeal> tmp = meals.stream()
                .filter(meal -> meal.getDateTime().toLocalTime().isAfter(startTime)
                        && meal.getDateTime().toLocalTime().isBefore(endTime));
        int calSum = tmp.mapToInt(UserMeal::getCalories).sum();
        boolean excess = calSum > caloriesPerDay;
        return tmp.map(meal ->
                        new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess))
                .collect(Collectors.toList());
    }
}
