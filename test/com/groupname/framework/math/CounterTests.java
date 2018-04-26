package com.groupname.framework.math;

import org.junit.Test;
import static org.junit.Assert.*;

import java.security.InvalidParameterException;

public class CounterTests {

    @Test(expected = InvalidParameterException.class)
    public void invalidWithZeroOrLess() {
        Counter counter = new Counter(-4);
    }

    @Test
    public void stepperSteps() {
        Counter counter = new Counter(1);
        assertEquals(counter.getCounter(), 0);
        counter.step();
        assertEquals(counter.getCounter(), 1);
    }

    @Test
    public void checkIsDone() {
        Counter counter = new Counter(1);
        assertFalse(counter.isDone());
        for(int i = 0; i < 60; i++) {
            counter.step();
        }
        assertTrue(counter.isDone());
    }

    @Test
    public void checkReset() {
        Counter counter = new Counter(1);
        assertEquals(counter.getCounter(), 0);
        counter.step();
        assertEquals(counter.getCounter(), 1);
        counter.reset();
        assertEquals(counter.getCounter(), 0);
    }
}
