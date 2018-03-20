package com.groupname.framework.math;

// May refactor this into SpriteRegion/TextureRegion instead
public class IntVector2D {

    private final IntVector2DMemento memento;

    private int x;
    private int y;

    //public final static Vector2D ZERO = Zero();
    public IntVector2D() {
        x = 0;
        y = 0;

        memento = new IntVector2DMemento(x, y);
    }

    public IntVector2D(int initialX, int initialY) {
        this.x = initialX;
        this.y = initialY;

        memento = new IntVector2DMemento(x, y);
    }

    public void reset() {
        x = memento.getX();
        y = memento.getY();
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void addX(int x) {
        this.x += x;
    }

    public void addY(int y) {
        this.y += y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // This class is only used internally so we can restore the original state of the object (a memento)
    private class IntVector2DMemento {
        private final int x;
        private final int y;

        private IntVector2DMemento(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Do we really need getters here?
        private int getX() {
            return x;
        }

        private int getY() {
            return y;
        }
    }
}
