package com.groupname.framework.util;

/**
 * A helper class meant for checking if a String instance is not null or contains an empty String.
 */
public final class Strings {
    private Strings() {
        throw new AssertionError("No Strings instances for you!");
    }

    /**
     * An empty String referenced, this String is interned and should only have one reference.
     */
    public static final String EMPTY = "";

    /**
     * Checks that the specified String reference is not {@code null} or is equal to an empty {@code ""} String.
     * This method is primarily designed to do parameter validation.
     *
     * @param value the String reference to check if it's null or contains an empty String.
     * @return {@code value} if not {@code null} or equal to {@code ""}
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws EmptyStringException if {@code value} is equal to {@code ""}
     */
    public static String requireNonNullAndNotEmpty(String value) {
        if(value == null) {
            throw new NullPointerException();
        }

        if("".equals(value)) {
            throw new EmptyStringException();
        }

        return value;
    }

    /**
     * Checks whether a String instance is null or is equal to an empty String.
     * @param value the String instance to check.
     * @return true if the String instance is null or is equal to an empty String, otherwise false.
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || "".equals(value);
    }

}
