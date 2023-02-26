package ru.javawebinar.topjava;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TestLoggingRule extends TestWatcher {

    private static final Logger log = LoggerFactory.getLogger(TestLoggingRule.class);
    private final Map<String, Long> testTime;

    public TestLoggingRule(Map<String, Long> testTime) {
        this.testTime = testTime;
    }

    @Override
    protected void finished(Description description) {
        testTime.forEach((s, aLong) -> log.debug("{} {}", s, aLong));
    }
}