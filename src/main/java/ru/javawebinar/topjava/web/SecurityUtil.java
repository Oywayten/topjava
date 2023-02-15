package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    static int testUserId;

    public static int authUserId() {
        return testUserId;
    }

    public static int setAuthUserId(int userId) {
        testUserId = userId;
        return testUserId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}