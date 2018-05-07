package com.groupname.framework.input.devices;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.security.InvalidParameterException;
import java.util.Objects;

/**
 * This class is used to grab mouse input from within a contained bounds rectangle.
 */
public class ContainedMouseInput {
    private ContainedMouseEvent onClicked;
    private ContainedMouseEvent onMove;

    private final Rectangle bounds;

    /**
     * Creates a new instance of this class and grabs input from the specified node.
     *
     * ContainedMouseEvents will only be fired when the mouse is moved or clicked on from with in the bounds specified.
     *
     * @param node the node to grab mouse input from.
     * @param bounds the bounds where ContainedMouseEvents will be fired.
     */
    public ContainedMouseInput(Node node, Rectangle bounds) {
        node.setOnMouseMoved(this::onMouseMove);
        node.setOnMouseClicked(this::onMouseClicked);

        this.bounds = Objects.requireNonNull(bounds);
        if(bounds.getWidth() <= 0 || bounds.getHeight() <= 0) {
            throw new InvalidParameterException();
        }
    }

    private void onMouseClicked(MouseEvent mouseEvent) {
        fireEventIfExists(onClicked, mouseEvent.getX(), mouseEvent.getY());
    }

    private void onMouseMove(MouseEvent mouseEvent) {
        fireEventIfExists(onMove, mouseEvent.getX(), mouseEvent.getY());
    }

    private void fireEventIfExists(ContainedMouseEvent event, double x, double y) {
        if(event != null && bounds.contains(x,y)) {
            event.onAction(x, y);
        }
    }

    /**
     * This ContainedMouseEvent will be fired if the mouse click was within the bounds when the event occurs.
     *
     * @param onClicked the event to fire up whenever the mouse was clicked within the bounds.
     */
    public void setOnClicked(ContainedMouseEvent onClicked) {
        this.onClicked = onClicked;
    }

    /**
     * This ContainedMouseEvent will be fired if the mouse moving was within the bounds when the event occurs.
     *
     * @param onMove the event to fire up whenever the mouse was moving within the bounds.
     */
    public void setOnMove(ContainedMouseEvent onMove) {
        this.onMove = onMove;
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "ContainedMouseInput{" +
                "onClicked=" + onClicked +
                ", onMove=" + onMove +
                ", bounds=" + bounds +
                '}';
    }
}

