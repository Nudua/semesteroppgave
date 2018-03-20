package com.groupname.util;

import com.groupname.framework.util.EmptyStringException;
import com.groupname.framework.util.Strings;
import org.junit.Test;
import static org.junit.Assert.*;


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
