package ru.javawebinar.topjava.util;

import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for UserMealsUtil class methods.
 * Created by Oywayten on 12.01.2023.
 */
class UserMealsUtilTest {
    /**
     * List {@link List} of meals {@link UserMeal} on which class methods are tested.
     */
    List<UserMeal> meals = Arrays.asList(
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new UserMeal(LocalDateTime.of(2022, Month.JANUARY, 31, 20, 0), "Ужин", 410));

    @Test
    void whenFilteredByStream3AndTime10To12AndCaloriesPerDay2000() {
        List<UserMealWithExcess> actual = UserMealsUtil.filteredByStream3(meals, LocalTime.of(10, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExcess> expectedShortList = Arrays.asList(
                new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 30, 10, 0), "Завтрак", 500, false),
                new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, true)
        );
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expectedShortList);
    }

    @Test
    void whenFilteredByStream3AndTime10To12AndCaloriesPerDay2000IsWrongListWith3Element() {
        List<UserMealWithExcess> actual = UserMealsUtil.filteredByStream3(meals, LocalTime.of(10, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExcess> expected = Arrays.asList(
                new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 30, 10, 0), "Завтрак", 500, false),
                new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 20, 10, 0), "Завтрак", 600, false),
                new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, true));
        assertThat(actual.size()).isLessThan(expected.size());
        assertThat(actual).hasAtLeastOneElementOfType(UserMealWithExcess.class);
    }

    @Test
    void whenFilteredByStream3AndTime00To2359AndCaloriesPerDay2000() {
        List<UserMealWithExcess> actual = UserMealsUtil.filteredByStream3(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        List<UserMealWithExcess> expectedLongList = Arrays.asList(
                new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 30, 10, 0), "Завтрак", 500, false),
                new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 30, 13, 0), "Обед", 1000, false),
                new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 30, 20, 0), "Ужин", 500, false),
                new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, true),
                new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, true),
                new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 31, 13, 0), "Обед", 500, true),
                new UserMealWithExcess(LocalDateTime.of(2022, Month.JANUARY, 31, 20, 0), "Ужин", 410, true));
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expectedLongList);
    }

    @Test
    void whenFilteredByStream3AndListIsEmpty() {
        List<UserMealWithExcess> actual = UserMealsUtil.filteredByStream3(new ArrayList<>(), LocalTime.of(10, 0), LocalTime.of(12, 0), 2000);
        assertThat(actual).isEmpty();
    }
}