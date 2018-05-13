package com.groupname.framework.graphics.background.transitions;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * A full screen arrow animation implementation of the ScreenTransition interface.
 *
 * This implementation will draw a triangle (arrow) flying across the screen from left to right until the screen is completely filled.
 * Users should check the isDone() method every frame to see if the animation has completed.
 */
public class ArrowScreenTransition implements ScreenTransition {

    private static final double END_POSITION = 1280 + 400;

    private GraphicsContext graphicsContext;

    private Point2D triangleStartPos = new Point2D(-200, 720 / 2);
    private Point2D trianglePosition = triangleStartPos;
    private boolean done = false;

    /**
     * Creates a new instance with the specified GraphicsContext that is used to draw this ScreenTransition.
     * @param graphicsContext the GraphicsContext to use to draw with.
     * @throws NullPointerException if graphicsContext is null.
     */
    public ArrowScreenTransition(GraphicsContext graphicsContext) {
        this.graphicsContext = Objects.requireNonNull(graphicsContext);
    }


    /**
     * Moves the arrow further towards the end position. (which is off screen)
     */
    public void update() {
        trianglePosition = trianglePosition.add(60,0);

        if(trianglePosition.getX() > END_POSITION) {
            done = true;
        }
    }

    /**
     * Draws the current full-screen animation used for this screen transition.
     */
    public void draw() {
        graphicsContext.save();

        drawTriangle();

        graphicsContext.restore();
    }

    /**
     * Resets the state of the animation of the screen-transition to the start.
     */
    public void reset() {
        done = false;
        trianglePosition = triangleStartPos;
    }

    /**
     * Returns if the screen-transition has completed its animation.
     *
     * @return true if the screen-transition has completed its animation, false otherwise.
     */
    public boolean isDone() {
        return done;
    }

    //todo, remember what these things do and comment them
    private void drawTriangle() {
        graphicsContext.setFill(Color.BLACK);
        Point2D[] triangle = buildTriangle(trianglePosition.getX(), trianglePosition.getY(), 720 / 2);

        if(trianglePosition.getX() > 0) {
            if(!done) {
                graphicsContext.fillRect(0,0, trianglePosition.getX(), 720);
            }
        }

        double[][] triangleCords = pointsToDoubleArray(triangle[0], triangle[1], triangle[2]);

        graphicsContext.fillPolygon(triangleCords[0], triangleCords[1], 3);
    }

    private double[][] pointsToDoubleArray(Point2D top, Point2D left, Point2D right) {
        return new double[][] { {top.getX(), left.getX(), right.getX()}, { top.getY(), left.getY(), right.getY()} };
    }

    private Point2D[] buildTriangle(double x, double y, double length) {

        Point2D top = new Point2D(x, y - length);
        Point2D bottom = new Point2D(x, y + length);
        Point2D middleRight = new Point2D(x + length, y);

        return new Point2D[] {top, bottom, middleRight};
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "ArrowScreenTransition{" +
                "graphicsContext=" + graphicsContext +
                ", triangleStartPos=" + triangleStartPos +
                ", trianglePosition=" + trianglePosition +
                ", done=" + done +
                '}';
    }
}
