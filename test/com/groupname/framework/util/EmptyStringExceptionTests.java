package com.groupname.framework.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class EmptyStringExceptionTests {

    @Test
    public void constructsNewInstanceWithSpecifiedMessage() {
        String message = "Something went wrong!";

        EmptyStringException emptyStringException = new EmptyStringException(message);

        assertEquals(emptyStringException.getMessage(), message);
    }

}
