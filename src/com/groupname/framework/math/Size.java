package com.groupname.framework.math;


import java.util.Objects;

/**
 * Has a width and a height. This class is immutable.
 */
public class Size {
    private int width;
    private int height;

    /**
     * Creates a new instance with width = 0 and height = 0.
     */
    public Size() {
        this.width = 0;
        this.height = 0;
    }

    /**
     * Creates a new instance with the specified width and height.
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
     * Method that tests if two size instances are equal.
     *
     * @param o the reference object with which to compare.
     * @return true if the width and height of this instance are considered the same as in 'o'.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Size size = (Size) o;

        return width == size.width &&
                height == size.height;
    }

    /**
     * Returns a hashcode for the contents of this object.
     *
     * @return a hashcode for the contents of this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(width, height);
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