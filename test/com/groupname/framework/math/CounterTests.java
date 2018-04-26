package com.groupname.framework.math;

import org.junit.Test;

import java.security.InvalidParameterException;

public class CounterTests {

    @Test(expected = InvalidParameterException.class)
    public void InvalidWithZeroOrLess() {
        Counter counter = new Counter(-4);
    }
}
