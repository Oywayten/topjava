package ru.javawebinar.topjava.util;

import java.time.LocalTime;

public final class TimeUtil {
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    private TimeUtil() {
    }
}
