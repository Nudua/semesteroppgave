package com.groupname.framework.serialization;

import java.util.Objects;

/**
 * This generic class holds two non-null objects.
 *
 * @param <T> the type of the first item to store.
 * @param <T2> the type of the second item to store.
 */
public class Tuple<T, T2> {
    private T item1;
    private T2 item2;

    /**
     * Creates a new instance of this Tuple with the specified items to store.
     * @param item1 the first item to store.
     * @param item2 the second item to store.
     */
    public Tuple(T item1, T2 item2) {
        this.item1 = Objects.requireNonNull(item1);
        this.item2 = Objects.requireNonNull(item2);
    }

    /**
     * Returns the first item.
     *
     * @return the first item.
     */
    public T getItem1() {
        return item1;
    }

    /**
     * Returns the second item.
     *
     * @return the second item.
     */
    public T2 getItem2() {
        return item2;
    }

    /**
     * Returns a String representation of the items stored by this Tuple.
     *
     * @return a String representation of the items stored by this Tuple.
     */
    @Override
    public String toString() {
        return "Tuple{" +
                "item1=" + item1 +
                ", item2=" + item2 +
                '}';
    }
}
