package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
     * калорий.
     */
    public static List<UserMealWithExcess> filteredByStream3(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesLimit) {

        class UserMealStore {
            private final List<UserMeal> userMeals;
            private final Predicate<UserMeal> predicate;
            private int caloriesAmount;
            private boolean excess;

            public UserMealStore(UserMeal meal, int caloriesLimit, Predicate<UserMeal> predicate) {
                caloriesAmount += meal.getCalories();
                excess = caloriesAmount > caloriesLimit;
                this.predicate = predicate;
                userMeals = new ArrayList<>();
                if (this.predicate.test(meal)) {
                    userMeals.add(meal);
                }
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

        return meals.parallelStream()
                .collect(Collector.of(HashMap::new,
                        (map, meal) -> map.merge(meal.getDate(), new UserMealStore(meal, caloriesLimit, userMeal ->
                                TimeUtil.isBetweenHalfOpen(userMeal.getTime(), startTime, endTime)), UserMealStore::merge),
                        (BinaryOperator<Map<LocalDate, UserMealStore>>) (map1, map2) -> {
                            map1.forEach((date, userMealStore) -> map2.merge(date, userMealStore, (UserMealStore::merge)));
                            return map2;
                        },
                        (map) -> map.values().stream()
                                .flatMap(userMealStore -> userMealStore.userMeals.stream()
                                        .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(),
                                                userMeal.getDescription(), userMeal.getCalories(), userMealStore.excess))
                                ).collect(Collectors.toList())));
    }
}
