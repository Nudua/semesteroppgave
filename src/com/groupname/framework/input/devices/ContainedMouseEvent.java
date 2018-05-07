package com.groupname.framework.input.devices;

/**
 * This functional interface represents either a move or click within a contained area of a node.
 */
public interface ContainedMouseEvent {
    /**
     * Implementations should fire up this event whenever a contained mouse event occurs.
     *
     * @param x the x-coordinate of the event.
     * @param y the y-coordinate of the event.
     */
    void onAction(double x, double y);
}
