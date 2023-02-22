package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int NOT_FOUND = 10;
    public static final int USER_MEAL1_ID = START_SEQ + 3;
    public static final int USER_MEAL2_ID = START_SEQ + 4;
    public static final int USER_MEAL3_ID = START_SEQ + 6;
    public static final int USER_MEAL4_ID = START_SEQ + 8;
    public static final int USER_MEAL5_ID = START_SEQ + 9;
    public static final int USER_MEAL6_ID = START_SEQ + 10;
    public static final int USER_MEAL7_ID = START_SEQ + 11;
    public static final int USER_MEAL8_ID = START_SEQ + 12;
    public static final int ADMIN_MEAL1_ID = START_SEQ + 5;
    public static final Meal userMeal1 = new Meal(USER_MEAL1_ID, LocalDateTime.of(2020, 4, 4, 20, 0), "apple", 150);
    public static final Meal userMeal2 = new Meal(USER_MEAL2_ID, LocalDateTime.of(2020, 1, 1, 1, 0), "cheese", 300);
    public static final Meal userMeal3 = new Meal(USER_MEAL3_ID, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal4 = new Meal(USER_MEAL4_ID, LocalDateTime.of(2020, 1, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal5 = new Meal(USER_MEAL5_ID, LocalDateTime.of(2020, 1, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal6 = new Meal(USER_MEAL6_ID, LocalDateTime.of(2020, 1, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal7 = new Meal(USER_MEAL7_ID, LocalDateTime.of(2020, 1, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal8 = new Meal(USER_MEAL8_ID, LocalDateTime.of(2020, 1, 31, 20, 21, 15), "Ужин", 410);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2019, 1, 1, 1, 1), "newMeal", 600);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal1);
        updated.setDateTime(LocalDateTime.of(2021, 5, 5, 21, 0));
        updated.setDescription("updateDescription");
        updated.setCalories(100);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
