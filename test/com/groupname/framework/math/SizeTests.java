package com.groupname.framework.math;

import org.junit.Test;
import static org.junit.Assert.*;

public class SizeTests {

    @Test
    public void InitializedWithZero() {
        Size size = new Size();

        assertTrue(size.getWidth() == 0.0d);
        assertTrue(size.getHeight() == 0.0d);
    }

    @Test
    public void InitializedWithCorrectValues() {
        Size size = new Size(640, 480);

        assertTrue(size.getWidth() == 640);
        assertTrue(size.getHeight() == 480);
    }

    @Test
    public void setWidthAndHeightSetsCorrectValue() {
        Size size = new Size();

        assertTrue(size.getWidth() == 0);
        assertTrue(size.getHeight() == 0);

        size.setHeight(300);
        size.setWidth(400);

        assertTrue(size.getHeight() == 300);
        assertTrue(size.getWidth() == 400);
    }

}
