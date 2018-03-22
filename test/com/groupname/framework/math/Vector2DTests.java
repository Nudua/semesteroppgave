package com.groupname.framework.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class Vector2DTests {

    @Test
    public void InitializedWithZero() {
        Vector2D vector2D = new Vector2D();
        assertEquals(vector2D.getX(), 0.0d, 0.0d);
        assertEquals(vector2D.getY(), 0.0d, 0.0d);

    }

    @Test
    public void initializeWithCorrectValues() {
        Vector2D vector2D = new Vector2D(100,1000);
        assertEquals(vector2D.getX(), 100, 0.0d);
        assertEquals(vector2D.getY(), 1000, 0.0d);
    }

    @Test
    public void setVectorToVector() {
        Vector2D source = new Vector2D(100,200);
        Vector2D testVector = new Vector2D(300, 400);

        testVector.set(source);
        assertEquals(source.getX(), testVector.getX(), 0.0d);
        assertEquals(source.getY(), testVector.getY(), 0.0d);

    }

    @Test
    public void addValueToVector() {
        Vector2D testVector = new Vector2D(100,100);
        testVector.add(10,10);

        assertEquals(testVector.getX(),110,0.0d);
        assertEquals(testVector.getY(), 110, 0.0d);
    }

    @Test
    public void addXaddYtoVector() {
        Vector2D testVector = new Vector2D(100,100);
        testVector.addY(10);
        testVector.addX(10);

        assertEquals(testVector.getX(),110,0.0d);
        assertEquals(testVector.getY(), 110, 0.0d);
    }

    @Test
    public void setXtoVector() {
        Vector2D testVector = new Vector2D();
        testVector.setX(100);
        assertEquals(testVector.getX(), 100, 0.0d);
    }

    @Test
    public void getXFromVector() {
        Vector2D testVector = new Vector2D(100,100);
        assertEquals(testVector.getX(), 100, 0.0d);
    }

    @Test
    public void setYtoVector() {
        Vector2D testVector = new Vector2D();
        testVector.setY(100);
        assertEquals(testVector.getY(),100, 0.0d);
    }

    @Test
    public void getYFromVector() {
        Vector2D testVector = new Vector2D(100,100);
        assertEquals(testVector.getY(), 100, 0.0d);
    }

    @Test
    public void chechIfVectorEqualsVector() {
        Vector2D testVector = new Vector2D(100,100);
        Vector2D source = new Vector2D(100,100);

        assertTrue(source.equals(testVector));
    }

    @Test
    public void testEquals_symmetric() {
        Vector2D testVector1 = new Vector2D(100,100);
        Vector2D testVector2 = new Vector2D(100,100);

        assertTrue(testVector1.equals(testVector2) && testVector2.equals(testVector1));
        assertTrue(testVector1.hashCode() == testVector2.hashCode());
    }

    @Test
    public void testToString() {
        Vector2D testVector = new Vector2D();
        String expected = "X: 0,000000, Y:0,000000";

        assertEquals(expected, testVector.toString());
    }
}
