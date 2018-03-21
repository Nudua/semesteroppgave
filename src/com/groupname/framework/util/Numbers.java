package com.groupname.framework.util;

//import javax.activation.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.util.Objects;

public final class Numbers {
    private Numbers() {
        throw new AssertionError("Can't instantiate this class.");
    }

    // Trying out something
    public static <T extends Number> boolean isNotNegative(T num) {
        Objects.requireNonNull(num);

        if(num instanceof Double || num instanceof Float) {
            return num.doubleValue() >= 0;
        } else if(num instanceof Short || num instanceof Integer || num instanceof Long) {
            return num.longValue() >= 0;
        } else {
            return new BigDecimal(num.toString()).compareTo(BigDecimal.ZERO) >= 0; // Equal or Greater than
        }
    }

}
