package ru.javawebinar.topjava.util;

import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестовый класс для методов класса UserMealsUtil.
 * Created by Oywayten on 12.01.2023.
 */
class UserMealsUtilTest {
    /**
     * Список {@link List} приемов еды {@link UserMeal} на котором тестируются методы класса.
     */
    List<UserMeal> meals = Arrays.asList(
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    List<UserMealWithExcess> expectedLongList = Arrays.asList(
            new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 30, 10, 0), "Завтрак", 500, false),
            new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 30, 13, 0), "Обед", 1000, false),
            new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 30, 20, 0), "Ужин", 500, false),
            new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, true),
            new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, true),
            new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 31, 13, 0), "Обед", 500, true),
            new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 31, 20, 0), "Ужин", 410, true));
    List<UserMealWithExcess> expectedShortList = Arrays.asList(
            new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 30, 10, 0), "Завтрак", 500, false),
            new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, true));

    @Test
    void whenFilteredByCyclesAndTime10To12AndCaloriesPerDay2000() {
        List<UserMealWithExcess> actual = UserMealsUtil.filteredByCycles(meals, LocalTime.of(10, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExcess> expected = expectedShortList;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenFilteredByCyclesAndTime00To2359AndCaloriesPerDay2000() {
        List<UserMealWithExcess> actual = UserMealsUtil.filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        List<UserMealWithExcess> expected = expectedLongList;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenFilteredByStreamsAndTime10To12AndCaloriesPerDay2000() {
        List<UserMealWithExcess> actual = UserMealsUtil.filteredByStreams(meals, LocalTime.of(10, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExcess> expected = expectedShortList;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenFilteredByStreamsAndTime00To2359AndCaloriesPerDay2000() {
        List<UserMealWithExcess> actual = UserMealsUtil.filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        List<UserMealWithExcess> expected = expectedLongList;
        assertThat(actual).isEqualTo(expected);
    }
}