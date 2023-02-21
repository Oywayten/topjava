package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int APPLE_ID = START_SEQ + 3;
    public static final int CHEESE_ID = START_SEQ + 4;
    public static final int COOKIES_ID = START_SEQ + 5;
    public static final Meal apple = new Meal(APPLE_ID, LocalDateTime.of(2020, 4, 4, 20, 0), "apple", 150);
    public static final Meal cheese = new Meal(CHEESE_ID, LocalDateTime.of(2020, 1, 1, 1, 0), "cheese", 300);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "newMeal", 600);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(apple);
        updated.setDateTime(LocalDateTime.of(2021, 5, 5, 21, 0));
        updated.setDescription("updateDescription");
        updated.setCalories(100);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
