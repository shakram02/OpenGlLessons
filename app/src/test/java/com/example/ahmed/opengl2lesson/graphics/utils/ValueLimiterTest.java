package com.example.ahmed.opengl2lesson.graphics.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ahmed on 11/22/17.
 */
public class ValueLimiterTest {
    @Test
    public void test_increment() {
        float delta = 0.5f;
        ValueLimiter limiter = new ValueLimiter(0, -1, 1, delta);

        limiter.increment();    // Should go to 0.5 by the time
        for (int i = 0; i < 10; i++) {
            assertTrue(limiter.getValue() < delta);
        }

        float finalValue = (float) limiter.getValue();
        assertTrue(finalValue >= delta);
        assertEquals(limiter.getValue(), finalValue, 0.05);
    }
}