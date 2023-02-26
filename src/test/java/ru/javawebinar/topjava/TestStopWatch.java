package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.HashMap;
import java.util.Map;

public class TestStopWatch extends Stopwatch {

    private static final Map<String, Long> testTime = new HashMap<>();

    public static Map<String, Long> getTestTime() {
        return testTime;
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        testTime.put(description.getMethodName(), nanos);
    }
}