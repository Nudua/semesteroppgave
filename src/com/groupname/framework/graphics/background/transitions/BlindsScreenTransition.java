package com.groupname.framework.graphics.background.transitions;

import com.groupname.game.data.AppSettings;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * A full screen blinds animation implementation of the ScreenTransition interface.
 *
 * This implementation will recursively draw blinds up the screen.
 * Users should check the isDone() method every frame to see if the animation has completed.
 */
public class BlindsScreenTransition implements ScreenTransition {
    private final GraphicsContext graphicsContext;

    private final int tileSize = 40;
    private final double speed = 1.0d;
    private final double targetHeight = tileSize + 4;

    private final double screenWidth = AppSettings.SCREEN_BOUNDS.getWidth();
    private final double screenHeight = AppSettings.SCREEN_BOUNDS.getHeight();

    private double currentHeight = 0;
    private boolean done = false;

    /**
     * Creates a new instance with the specified GraphicsContext that is used to draw this ScreenTransition.
     * @param graphicsContext the GraphicsContext to use to draw with.
     * @throws NullPointerException if graphicsContext is null.
     */
    public BlindsScreenTransition(GraphicsContext graphicsContext) {
        this.graphicsContext = Objects.requireNonNull(graphicsContext);
    }

    /**
     * Increases the state of the blinds animation.
     */
    @Override
    public void update() {
        currentHeight+= speed;

        if(currentHeight >= targetHeight) {
            currentHeight = 0;
            done = true;
        }
    }

    /**
     * Draws the current full-screen animation used for this screen transition.
     */
    @Override
    public void draw() {
        int numTiles = (int)screenHeight / tileSize;

        drawBlinds(numTiles - 1);
    }

    private void drawBlinds(int max) {
        if(max < 0) {
            return;
        }

        double y = max * tileSize;
        // Draw each blind recursively from the bottom of the screen until we reach the top
        graphicsContext.fillRect(0, y, screenWidth, currentHeight);

        drawBlinds(max - 1);
    }

    /**
     * Resets the state of the animation of the screen-transition to the start.
     */
    @Override
    public void reset() {
        currentHeight = 0;
        done = false;
    }

    /**
     * Returns if the screen-transition has completed its animation.
     *
     * @return true if the screen-transition has completed its animation, false otherwise.
     */
    public boolean isDone() {
        return done;
    }
    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "BlindsScreenTransition{" +
                "graphicsContext=" + graphicsContext +
                ", tileSize=" + tileSize +
                ", speed=" + speed +
                ", targetHeight=" + targetHeight +
                ", screenWidth=" + screenWidth +
                ", screenHeight=" + screenHeight +
                ", currentHeight=" + currentHeight +
                ", done=" + done +
                '}';
    }
}
