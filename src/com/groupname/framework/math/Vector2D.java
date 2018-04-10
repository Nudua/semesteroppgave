package com.groupname.framework.math;

import java.util.Objects;

public class Vector2D {

    private double x;
    private double y;

    public Vector2D() {
        x = 0;
        y = 0;
    }

    public Vector2D(Vector2D position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    public Vector2D(double initialX, double initialY) {
        this.x = initialX;
        this.y = initialY;
    }

    public void set(Vector2D vector2D) {
        this.x = vector2D.getX();
        this.y = vector2D.getY();
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public Vector2D normalize(Vector2D other) {
        return new Vector2D(x - other.getX(), y - other.getY());
    }

    public void addX(double x) {
        this.x += x;
    }

    public void addY(double y) {
        this.y += y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("X: %f, Y:%f", x, y);
    }
}
