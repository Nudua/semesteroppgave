package com.groupname.framework.graphics.background.transitions;

/**
 * Represents a full-screen animation that is meant to be run
 * when the user starts or completes a Level.
 */
public interface ScreenTransition {

    /**
     * Updates any logic required to further the animation of the screen transition.
     */
    void update();

    /**
     * Draws the current full-screen animation used for this screen transition.
     */
    void draw();

    /**
     * Resets the state of the animation of the screen-transition to the start.
     */
    void reset();

    /**
     * Returns if the screen-transition has completed its animation.
     *
     * @return true if the screen-transition has completed its animation, false otherwise.
     */
    boolean isDone();
}