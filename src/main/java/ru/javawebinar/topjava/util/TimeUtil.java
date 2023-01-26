package ru.javawebinar.topjava.util;

import java.time.LocalTime;

/**
 * Utility class for methods for working with time.
 */
public class TimeUtil {
    /**
     * Метод получает {@link LocalTime} для проверки, что он находится в диапазоне между startTime(включительно) и endTime(исключительно).
     *
     * @param lt        {@link LocalTime} for check.
     * @param startTime {@link LocalTime} start time including
     * @param endTime   {@link LocalTime} end time (excluding)
     * @return true if lt is in range; false if not.
     */
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return !lt.isBefore(startTime) && lt.isBefore(endTime);
    }
}
