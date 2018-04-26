package com.groupname.framework.math;

import org.junit.Test;
import static org.junit.Assert.*;

public class IntVector2DTests {

    @Test
    public void InitializedWithZero() {
        IntVector2D vector2D = new IntVector2D();
        assertEquals(vector2D.getX(), 0.0d, 0.0d);
        assertEquals(vector2D.getY(), 0.0d, 0.0d);

    }

    @Test
    public void initializeWithCorrectValues() {
        IntVector2D vector2D = new IntVector2D(100,1000);
        assertEquals(vector2D.getX(), 100, 0.0d);
        assertEquals(vector2D.getY(), 1000, 0.0d);
    }


    @Test
    public void addValueToVector() {
        IntVector2D testVector = new IntVector2D(100,100);
        testVector.add(10,10);

        assertEquals(testVector.getX(),110,0.0d);
        assertEquals(testVector.getY(), 110, 0.0d);
    }

    @Test
    public void addXaddYtoVector() {
        IntVector2D testVector = new IntVector2D(100,100);
        testVector.addY(10);
        testVector.addX(10);

        assertEquals(testVector.getX(),110,0.0d);
        assertEquals(testVector.getY(), 110, 0.0d);
    }

    @Test
    public void setXtoVector() {
        IntVector2D testVector = new IntVector2D();
        testVector.setX(100);
        assertEquals(testVector.getX(), 100, 0.0d);
    }

    @Test
    public void getXFromVector() {
        IntVector2D testVector = new IntVector2D(100,100);
        assertEquals(testVector.getX(), 100, 0.0d);
    }

    @Test
    public void setYtoVector() {
        IntVector2D testVector = new IntVector2D();
        testVector.setY(100);
        assertEquals(testVector.getY(),100, 0.0d);
    }

    @Test
    public void getYFromVector() {
        IntVector2D testVector = new IntVector2D(100,100);
        assertEquals(testVector.getY(), 100, 0.0d);
    }

    @Test
    public void chechIfVectorEqualsVector() {
        IntVector2D testVector = new IntVector2D(100,100);
        IntVector2D source = new IntVector2D(100,100);

        assertTrue(source.equals(testVector));
    }

    @Test
    public void testEquals_symmetric() {
        IntVector2D testVector1 = new IntVector2D(100,100);
        IntVector2D testVector2 = new IntVector2D(100,100);

        assertTrue(testVector1.equals(testVector2) && testVector2.equals(testVector1));
        assertTrue(testVector1.hashCode() == testVector2.hashCode());
    }

    @Test
    public void testToString() {
        IntVector2D testVector = new IntVector2D();
        String expected = "IntVector2D{x=0, y=0}";

        assertEquals(expected, testVector.toString());
    }
}

