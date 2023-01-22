package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Excess;
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
                new UserMeal(LocalDateTime.of(
                        2022, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(
                        2022, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(
                        2022, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(
                        2022, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(
                        2022, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(
                        2022, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(
                        2022, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        List<UserMealWithExcess> mealsTo = filteredByCycles(
                meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println(filteredByStreams(
                meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000));
    }


    /**
     * HW0.
     * Метод получает список приемов еды для проверки было ли превышение
     * заданного калоража в указанном периоде. Метод реализован на циклах.
     *
     * @param meals          список еды {@link UserMeal}
     * @param startTime      начало ежедневного временного интервала
     * @param endTime        конец ежедневного временного интервала
     * @param caloriesPerDay int максимальное количество калорий для интервала
     * @return {@link UserMealWithExcess} список записей с флагом избытка
     * калорий.
     */
    public static List<UserMealWithExcess> filteredByCycles(
            List<UserMeal> meals,
            LocalTime startTime,
            LocalTime endTime,
            int caloriesPerDay) {
        Map<LocalDate, Integer> dateCaloriesMap = new HashMap<>();
        List<UserMealWithExcess> result = new LinkedList<>();
        for (UserMeal meal : meals) {
            dateCaloriesMap.merge(
                    meal.getDate(),
                    meal.getCalories(),
                    Integer::sum
            );
        }
        for (UserMeal meal : meals) {
            final boolean excess =
                    dateCaloriesMap.get(meal.getDate()) > caloriesPerDay;
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(),
                    startTime, endTime)) {
                result.add(new UserMealWithExcess(meal, excess));
            }
        }
        return result;
    }

    /**
     * Optional with Stream API.
     * Метод получает список приемов еды для проверки было ли превышение
     * заданного калоража в указанном периоде. Метод реализован на стримах.
     *
     * @param meals          список еды {@link UserMeal}
     * @param startTime      начало ежедневного временного интервала
     * @param endTime        конец ежедневного временного интервала
     * @param caloriesPerDay int максимальное количество калорий для интервала
     * @return {@link UserMealWithExcess} список записей с флагом избытка
     * калорий.
     */
    public static List<UserMealWithExcess> filteredByStreams(
            List<UserMeal> meals, LocalTime startTime,
            LocalTime endTime, int caloriesPerDay) {
        final Map<LocalDate, Integer> dateCaloriesMap = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate,
                        HashMap::new, Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(
                        userMeal.getTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExcess(
                        userMeal,
                        dateCaloriesMap.get(userMeal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    /**
     * Optional2 by cycle.
     * Метод получает список приемов еды для проверки было ли превышение
     * заданного калоража в указанном периоде. Метод реализован на циклах.
     *
     * @param meals          список еды {@link UserMeal}
     * @param startTime      начало ежедневного временного интервала
     * @param endTime        конец ежедневного временного интервала
     * @param caloriesPerDay int максимальное количество калорий для интервала
     * @return {@link UserMealWithExcess} список записей с флагом избытка
     * калорий.
     */
    public static List<UserMealWithExcess> filteredByCycles2(
            List<UserMeal> meals,
            LocalTime startTime,
            LocalTime endTime,
            int caloriesPerDay) {
        Map<LocalDate, Excess> dateExcessMap = new HashMap<>();
        List<UserMealWithExcess> result = new LinkedList<>();
        for (UserMeal meal : meals) {
            final Excess merge = dateExcessMap.merge(
                    meal.getDate(),
                    new Excess(caloriesPerDay, meal.getCalories()),
                    (excess, excess2) -> {
                        excess.addCalories(meal.getCalories());
                        return excess;
                    }
            );
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                result.add(new UserMealWithExcess(meal, merge));
            }
        }
        return result;
    }

    /**
     * Optional2 by Stream API.
     * Метод получает список приемов еды для проверки было ли превышение
     * заданного калоража в указанном периоде. Метод реализован на Stream API.
     *
     * @param meals          список еды {@link UserMeal}
     * @param startTime      начало ежедневного временного интервала
     * @param endTime        конец ежедневного временного интервала
     * @param caloriesPerDay int максимальное количество калорий для интервала
     * @return {@link UserMealWithExcess} список записей с флагом избытка
     * калорий.
     */
    public static List<UserMealWithExcess> filteredByStream2(
            List<UserMeal> meals,
            LocalTime startTime,
            LocalTime endTime,
            int caloriesPerDay) {
        Map<LocalDate, Excess> dateExcessMap = new HashMap<>();
        return meals.stream().peek(meal -> dateExcessMap.merge(
                        meal.getDate(),
                        new Excess(caloriesPerDay, meal.getCalories()),
                        (excess, excess2) -> {
                            excess.addCalories(meal.getCalories());
                            return excess;
                        }
                ))
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(
                        userMeal.getTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal, dateExcessMap.get(meal.getDate())))
                .collect(Collectors.toList());
    }
}
