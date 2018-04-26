package com.groupname.framework.math;


/**
 * Has a width and a height. This class is immutable.
 */
public class Size {
    private int width;
    private int height;

    /**
     * Constructure with the height and width zero.
     */
    public Size() {
        this.width = 0;
        this.height = 0;
    }

    /**
     * Constructure where you set the width and the height.
     *
     * @param width your width.
     * @param height your height.
     */
    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Return the width.
     *
     * @return the width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return the height.
     *
     * @return the height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the width and height as a string representation of the object.
     *
     * @return the width and height as a string representation of the object.
     */
    @Override
    public String toString() {
        return "Size{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}