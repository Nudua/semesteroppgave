package com.groupname.tests;

import com.groupname.framework.math.ImmutableVector2D;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

// Test test
public class ImmutableVectorTest {

    @Test
    public void InitalizedWithZero() {
        ImmutableVector2D vec = new ImmutableVector2D();
        assertTrue(vec.getX() == 0);
        assertTrue(vec.getY() == 0);
    }

    @Test
    public void InitalizedWithCorrectValue() {
        ImmutableVector2D vec = new ImmutableVector2D(20, 23);
        assertTrue(vec.getX() == 20);
        assertTrue(vec.getY() == 23);
    }

    @Test
    public void AddGivesCorrectValue() {
        ImmutableVector2D vec = new ImmutableVector2D(100, 200);

        double x = 145.4;
        double y = 648.9;

        ImmutableVector2D newValue = vec.add(x, y);

        assertTrue(newValue.getX() == vec.getX() + x);
        assertTrue(newValue.getY() == vec.getY() + y);
    }
}
