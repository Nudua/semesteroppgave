package com.groupname.framework.math;

import java.util.Objects;

// Might just stick to the mutable vector
public class ImmutableVector2D {

    private final double x;
    private final double y;

    public static final ImmutableVector2D ZERO = new ImmutableVector2D();
    public static final ImmutableVector2D ONE = new ImmutableVector2D(1,1);

    public ImmutableVector2D() {
        x = 0;
        y = 0;
    }

    public ImmutableVector2D(ImmutableVector2D source) {
        this.x = source.x;
        this.y = source.y;
    }

    public ImmutableVector2D(double initialX, double initialY) {
        this.x = initialX;
        this.y = initialY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Same as new, remove?
    public ImmutableVector2D set(double x, double y) {
        return new ImmutableVector2D(x, y);
    }

    public ImmutableVector2D add(double x, double y) {
        return new ImmutableVector2D(this.x + x, this.y + y);
    }

    public ImmutableVector2D addX(double x) {
        return new ImmutableVector2D(this.x + x, this.y);
    }

    public ImmutableVector2D addY(double y) {
        return new ImmutableVector2D(this.x, this.y + y);
    }

    public ImmutableVector2D setX(double x) {
        return new ImmutableVector2D(x, this.y);
    }

    public ImmutableVector2D setY(double y) {
        return new ImmutableVector2D(this.x, y);
    }

    public ImmutableVector2D getCopy() {
        return new ImmutableVector2D(this);
    }

    public double[] toArray() {
        return new double[] {x, y};
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if(!(o instanceof ImmutableVector2D)) {
            return false;
        }

        ImmutableVector2D vector2D = (ImmutableVector2D)o;

        return this.x == vector2D.x &&
                this.y == vector2D.y;
    }

    // Cached hashCode
    private int hashCode;

    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = Double.hashCode(x);
            result = 31 * result + Double.hashCode(y);
            hashCode = result;
        }
        return result;
    }

    // One line hashCode
    public int hashCodeEx() {
        return Objects.hash(x, y);
    }

    public int hashCodeEx2() {
        return 31 * Double.hashCode(x) + Double.hashCode(y);
    }
}