package com.groupname.framework.math;

import java.io.Serializable;
import java.util.Objects;

/**
 * A position vector with x and y.
 * x and y are doubles.
 */
public class Vector2D implements Serializable {

    private double x;
    private double y;

    /**
     * Constructor for a vector with x = zero and y = zero.
     */
    public Vector2D() {
        x = 0;
        y = 0;
    }

    /**
     * Constructor that copies another vector.
     *
     * @param position to the vector you copies.
     */
    public Vector2D(Vector2D position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    /**
     * Constructor for Vector2D with x and y parameters.
     *
     * @param initialX sets the x, as a double.
     * @param initialY sets the y, as a double.
     */
    public Vector2D(double initialX, double initialY) {
        this.x = initialX;
        this.y = initialY;
    }

    /**
     * Copies the x and y from source to the target vector.
     *
     * @param vector2D the source vector.
     */
    public void set(Vector2D vector2D) {
        this.x = vector2D.getX();
        this.y = vector2D.getY();
    }

    /**
     * Set new values to an existing vector.
     *
     * @param x new x value.
     * @param y new y value.
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Add value to x and y.
     *
     * @param x the x value you want to add.
     * @param y the y value you want to add.
     */
    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Method for subtracting two vectors.
     *
     * @param other the subtracting vector.
     * @return a new vector.
     */
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(x - other.getX(), y - other.getY());
    }

    /**
     * Add x value to the existing x value.
     *
     * @param x value you want to add.
     */
    public void addX(double x) {
        this.x += x;
    }

    /**
     * Add y value to the existing y value.
     *
     * @param y value you want to add.
     */
    public void addY(double y) {
        this.y += y;
    }

    /**
     * Return x value.
     * @return x value.
     */
    public double getX() {
        return x;
    }

    /**
     * Setter for x.
     *
     * @param x value to set.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Return y value.
     *
     * @return y value.
     */
    public double getY() {
        return y;
    }

    /**
     * Setter for y.
     *
     * @param y value to set.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Method that tests if two vectors are equal.
     *
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if(!(o instanceof Vector2D)) {
            return false;
        }

        Vector2D vector = (Vector2D)o;

        return this.x == vector.x &&
                this.y == vector.y;
    }

    /**
     * Returns a hash code value for the object
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns the x and y coordinate as a string representation of the object.
     *
     * @return the x and y coordinate as a string representation of the object.
     */
    @Override
    public String toString() {
        return String.format("X: %f, Y:%f", x, y);
    }
}
