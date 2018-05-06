package com.groupname.framework.serialization;

import java.util.Objects;

public class Tuple<T, T2> {

    private T item1;
    private T2 item2;

    public Tuple(T item1, T2 item2) {
        this.item1 = Objects.requireNonNull(item1);
        this.item2 = Objects.requireNonNull(item2);
    }

    public T getItem1() {
        return item1;
    }

    public T2 getItem2() {
        return item2;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "item1=" + item1 +
                ", item2=" + item2 +
                '}';
    }
}
