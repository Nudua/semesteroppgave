package com.groupname.framework.math;

import java.util.Objects;

/**
 * A position vector with x and y.
 * x and y are ints.
 */
public class IntVector2D {
    private int x;
    private int y;

    //public final static Vector2D ZERO = Zero();

    /**
     * Constructor for a vector with x = zero and y = zero.
     */
    public IntVector2D() {
        x = 0;
        y = 0;
    }

    /**
     * Constructor for IntVector2D with x and y parameters.
     *
     * @param initialX sets the x, as a int.
     * @param initialY sets the y, as a int.
     */
    public IntVector2D(int initialX, int initialY) {
        this.x = initialX;
        this.y = initialY;

    }

    /**
     * Set new values to an existing vector.
     *
     * @param x new x value.
     * @param y new y value.
     */
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Add value to x and y.
     *
     * @param x the x value you want to add.
     * @param y the y value you want to add.
     */
    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Add x value to the existing x value.
     *
     * @param x value you want to add.
     */
    public void addX(int x) {
        this.x += x;
    }

    /**
     * Add y value to the existing x value.
     *
     * @param y value you want to add.
     */
    public void addY(int y) {
        this.y += y;
    }

    /**
     * Return x value.
     * @return x value.
     */
    public int getX() {
        return x;
    }

    /**
     * Setter for x.
     *
     * @param x value to set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Return y value.
     *
     * @return y value.
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        IntVector2D that = (IntVector2D) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Setter for y.
     *
     * @param y value to set.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the x and y coordinate as a string representation of the object.
     *
     * @return the x and y coordinate as a string representation of the object.
     */
    @Override
    public String toString() {
        return "IntVector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
