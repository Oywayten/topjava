package ru.javawebinar.topjava.util;

import java.time.LocalTime;

/**
 * Утилитный класс для методов по работе со временем.
 */
public class TimeUtil {
    public static boolean isBetweenHalfOpen(
            LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return !lt.isBefore(startTime) && lt.isBefore(endTime);
    }
}
