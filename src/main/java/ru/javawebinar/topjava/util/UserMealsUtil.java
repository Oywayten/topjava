package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealStore;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Utility class on nutrition.
 */
public class UserMealsUtil {
    /**
     * A method for simply checking the result of the class.
     *
     * @param args method arguments (not used).
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
        System.out.println(filteredByStream3(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000));
    }

    /**
     * Optional2 by Stream API with custom Collector.
     * The method receives a list of meals to check whether the specified calorie intake was exceeded in the specified period.
     * The method is implemented on the Stream API.
     *
     * @param meals         food list {@link UserMeal}
     * @param startTime     the start of the daily time interval.
     * @param endTime       end of the daily time slot.
     * @param caloriesLimit int the maximum number of calories for the interval.
     * @return {@link UserMealStore} a list of entries with a calorie surplus flag.
     * калорий.
     */
    public static List<UserMealWithExcess> filteredByStream3(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesLimit) {
        return meals.parallelStream()
                .collect(Collector.of(HashMap::new,
                        (map, meal) -> map.merge(meal.getDate(), new UserMealStore(meal.getDate(), caloriesLimit, userMeal1 ->
                                TimeUtil.isBetweenHalfOpen(userMeal1.getTime(), startTime, endTime))
                                .addMeal(meal), UserMealStore::merge),
                        (BinaryOperator<Map<LocalDate, UserMealStore>>) (map21, map2) -> {
                            map21.forEach((date, userMealStore) -> map2.merge(date, userMealStore, (UserMealStore::merge)));
                            return map2;
                        },
                        (map1) -> map1.values().stream()
                                .flatMap(userMealStore -> userMealStore.getUserMeals().stream()
                                        .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(),
                                                userMeal.getDescription(), userMeal.getCalories(), userMealStore.isExcess()))
                                ).collect(Collectors.toList())));
    }
}
