package com.groupname.framework.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringsTest {

    @Test(expected = NullPointerException.class)
    public void requireNonNullAndNotEmptyThrowsOnNull() {
        Strings.requireNonNullAndNotEmpty(null);
    }

    @Test(expected = EmptyStringException.class)
    public void requireNonNullAndNotEmptyThrowsOnEmpty() {
        Strings.requireNonNullAndNotEmpty("");
    }

    @Test
    public void isNullOrEmptyReturnsTrueOnNull() {
        assertTrue(Strings.isNullOrEmpty(null));
    }

    @Test
    public void isNullOrEmptyReturnsTrueOnEmpty() {
        assertTrue(Strings.isNullOrEmpty(""));
    }

    @Test
    public void isNullOrEmptyReturnsFalseOnValidString() {
        assertTrue(Strings.isNullOrEmpty("Hello Test"));
    }
}
