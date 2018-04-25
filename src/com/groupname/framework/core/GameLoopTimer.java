package com.groupname.framework.core;

import javafx.animation.AnimationTimer;

import java.util.Objects;

/**
 * This class extends the AnimationTimer class and is used to
 * update and draw at a frequency at 60hz (60 frames per second).
 *
 * It will call the update and draw Runnables once every frame.
 */
public class GameLoopTimer extends AnimationTimer {
    private final Runnable update;
    private final Runnable draw;

    /**
     * Creates a new instance with the specified update and draw Runnables (methods) to be executed every frame.
     * @param update the Runnable to update every frame, should update game logic.
     * @param draw the Runnable to update every frame, should be used to draw the world.
     */
    public GameLoopTimer(Runnable update, Runnable draw) {
        this.update = Objects.requireNonNull(update);
        this.draw = Objects.requireNonNull(draw);
    }

    /**
     * Overwritten from the AnimationTimer super class, this method will be called once every frame.
     * Usually at a frequency of 60hz per second.
     *
     * It is used to call our update and draw Runnables every frame.
     *
     * @param now timestamp of the current frame. (nanoseconds)
     */
    @Override
    public void handle(long now) {
        update.run();
        draw.run();
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "GameLoopTimer{" +
                "update=" + update +
                ", draw=" + draw +
                '}';
    }
}
