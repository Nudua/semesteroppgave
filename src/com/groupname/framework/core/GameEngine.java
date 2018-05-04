package com.groupname.framework.core;

import com.groupname.framework.math.Size;
import javafx.animation.*;
import javafx.scene.canvas.Canvas;

import javafx.scene.paint.Color;

/**
 * The base class used to drive a GAME which contains an AnimatingTimer that
 * updates 60 times per second.
 *
 * Subclasses must override update (logic) and draw (used to draw the game world).
 */
public abstract class GameEngine {

    protected Canvas canvas;

    protected int width;
    protected int height;

    private volatile boolean running = false;

    protected Color background = Color.CORNFLOWERBLUE;

    private AnimationTimer animationTimer;

    /**
     * Subclasses must call this method with the specified width and height for the Canvas.
     *
     * @param width the width of the game area.
     * @param height the height of the game area.
     */
    public GameEngine(int width, int height) {
        this.width = width;
        this.height = height;

        createAnimationTimer();
    }

    private void createAnimationTimer() {
        animationTimer = new GameLoopTimer(this::update, this::draw);
    }

    /**
     * Returns the bounds (Size) of the game area.
     *
     * @return the bounds (Size) of the game area.
     */
    public Size getScreenBounds() {
        return new Size(width, height);
    }

    /**
     * Returns whether the game loop is running or not.
     *
     * @return true if the game loop is running, false otherwise.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Starts the game loop if it's not already running.
     */
    public void start() {
        if(running) {
            return;
        }

        running = true;
        animationTimer.start();
    }

    /**
     * Stops the game loop.
     */
    public void stop() {
        running = false;
        animationTimer.stop();
    }

    /**
     * Implementations must override this to update any game logic, this method will be called 60 times per second.
     */
    protected abstract void update();

    /**
     * Implementations must override this to draw the game world, this method will be called 60 times per second.
     */
    protected abstract void draw();
}
