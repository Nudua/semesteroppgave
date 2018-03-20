package com.groupname.framework.util;

import org.junit.Test;


public class StringsTest {

    @Test(expected = NullPointerException.class)
    public void checkNullPointerException() {
        Strings.requireNonNullAndNotEmpty(null);
    }

    @Test(expected = EmptyStringException.class)
    public void checkEmptyStingExeption() {
        Strings.requireNonNullAndNotEmpty("");
    }
}
