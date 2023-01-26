package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
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
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000));
    }

    /**
     * Utility method converts UserMeal with boolean to UserMealWithExcess.
     */
    private static UserMealWithExcess getUserMealWithExcess1(UserMeal userMeal, boolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }

    /**
     * Utility method converts UserMeal with UserMealWithExcess.Excess to UserMealWithExcess.
     */
    private static UserMealWithExcess getUserMealWithExcess2(UserMeal userMeal, UserMealWithExcess.Excess excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }

    /**
     * HW0.
     * The method receives a list of meals to check whether the specified calorie intake was exceeded in the specified period.
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
                result.add(getUserMealWithExcess1(meal, excess));
            }
        }
        return result;
    }

    /**
     * Optional with Stream API.
     * The method receives a list of meals to check whether the specified calorie intake was exceeded in the specified period.
     * The method is implemented on streams.
     *
     * @param meals          food list {@link UserMeal}.
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
                .map(userMeal -> getUserMealWithExcess1(userMeal, dateCaloriesMap.get(userMeal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    /**
     * Optional2 by cycle.
     * The method receives a list of meals to check whether the specified calorie intake was exceeded in the specified period.
     * The method is implemented on cycles.
     *
     * @param meals          food list {@link UserMeal}
     * @param startTime      the start of the daily time interval.
     * @param endTime        end of the daily time slot.
     * @param caloriesPerDay int the maximum number of calories for the interval.
     * @return {@link UserMealWithExcess} a list of entries with a calorie surplus flag.
     */
    public static List<UserMealWithExcess> filteredByCycles2(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, UserMealWithExcess.Excess> dateExcessMap = new HashMap<>();
        List<UserMealWithExcess> result = new LinkedList<>();
        for (UserMeal meal : meals) {
            final UserMealWithExcess.Excess merge = dateExcessMap.merge(meal.getDate(),
                    new UserMealWithExcess.Excess(caloriesPerDay, meal.getCalories()),
                    (excess, excess2) -> {
                        excess.addCalories(meal.getCalories());
                        return excess;
                    }
            );
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                result.add(getUserMealWithExcess2(meal, merge));
            }
        }
        return result;
    }

    /**
     * Optional2 by Stream API.
     * The method receives a list of meals to check whether the specified calorie intake was exceeded in the specified period.
     * The method is implemented on the Stream API.
     *
     * @param meals          food list {@link UserMeal}
     * @param startTime      the start of the daily time interval.
     * @param endTime        end of the daily time slot.
     * @param caloriesPerDay int the maximum number of calories for the interval.
     * @return {@link UserMealWithExcess} a list of entries with a calorie surplus flag.
     * калорий.
     */
    public static List<UserMealWithExcess> filteredByStream2(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, UserMealWithExcess.Excess> dateExcessMap = new HashMap<>();
        return meals.stream().
                peek(meal -> dateExcessMap.merge(meal.getDate(), new UserMealWithExcess.Excess(caloriesPerDay, meal.getCalories()),
                        (excess, excess2) -> {
                            excess.addCalories(meal.getCalories());
                            return excess;
                        }
                ))
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getTime(), startTime, endTime))
                .map(meal -> getUserMealWithExcess2(meal, dateExcessMap.get(meal.getDate()))).collect(Collectors.toList());
    }

    /**
     * Optional2 by Stream API with custom Collector.
     * The method receives a list of meals to check whether the specified calorie intake was exceeded in the specified period.
     * The method is implemented on the Stream API.
     *
     * @param meals          food list {@link UserMeal}
     * @param startTime      the start of the daily time interval.
     * @param endTime        end of the daily time slot.
     * @param caloriesPerDay int the maximum number of calories for the interval.
     * @return {@link UserMealWithExcess} a list of entries with a calorie surplus flag.
     * калорий.
     */
    public static List<UserMealWithExcess> filteredByStream3(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Supplier<Map<LocalDate, List<UserMealWithExcess>>> supplier = HashMap::new;
        BiConsumer<Map<LocalDate, List<UserMealWithExcess>>, UserMeal> consumer = (map, meal) ->
                map.merge(meal.getDate(), new ArrayList<UserMealWithExcess>() {{
                    add(getUserMealWithExcess2(meal, new UserMealWithExcess.Excess(caloriesPerDay, meal.getCalories())));
                }}, (excesses, excesses2) -> {
                    final UserMealWithExcess.Excess excess = excesses.get(0).getExcess();
                    excesses2.get(0).setExcess(excess);
                    excesses.addAll(excesses2);
                    return excesses;
                });
        BinaryOperator<Map<LocalDate, List<UserMealWithExcess>>> merger = (map, map2) -> {
            map.putAll(map2);
            return map;
        };
        Function<Map<LocalDate, List<UserMealWithExcess>>, List<UserMealWithExcess>> function = (map) ->
                map.entrySet().stream()
                        .flatMap(entry -> entry.getValue().stream())
                        .filter(excess -> TimeUtil.isBetweenHalfOpen(excess.getDateTime().toLocalTime(), startTime, endTime))
                        .collect(Collectors.toList());
        return meals.stream().collect(Collector.of(supplier, consumer, merger, function));
    }
}
