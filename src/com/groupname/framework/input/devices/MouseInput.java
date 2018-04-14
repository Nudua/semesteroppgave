package com.groupname.framework.input.devices;

import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.security.InvalidParameterException;
import java.util.Objects;

// Figure out a better name
public class MouseInput {

    private boolean pressed;
    private TileEvent onClicked;
    private TileEvent onMove;

    private final Rectangle bounds;

    public MouseInput(Node scene, Rectangle bounds) {
        scene.setOnMouseMoved(this::onMouseMove);
        scene.setOnMouseClicked(this::onMouseClicked);

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

    private void fireEventIfExists(TileEvent event, double x, double y) {
        if(event != null && bounds.contains(x,y)) {
            event.onAction(x, y);
        }
    }

    public void setOnClicked(TileEvent onClicked) {
        this.onClicked = onClicked;
    }

    public void setOnMove(TileEvent onHover) {
        this.onMove = onHover;
    }
}
/*
public class MouseInput {

    private boolean pressed;
    private Vector2D pressedCoordinates = new Vector2D();
    private Vector2D movingCoordinates = new Vector2D();

    public MouseInput(Node scene) {
        Objects.requireNonNull(scene);

        scene.setOnMouseMoved(this::onMouseMove);
        scene.setOnMousePressed(this::onMousePressed);
        scene.setOnMouseReleased(this::onMouseReleased);
        scene.setOnMouseDragged(this::onMouseMove);
    }

    public boolean isPressed() {
        return pressed;
    }

    public Vector2D getPressedCoordinates() {
        return new Vector2D(pressedCoordinates);
    }

    public Vector2D getMovingCoordinates() {
        return new Vector2D(movingCoordinates);
    }

    private void onMouseReleased(MouseEvent event) {
        pressed = false;
    }

    private void onMousePressed(MouseEvent event) {
        pressed = true;
        pressedCoordinates.set(event.getX(), event.getY());
    }

    private void onMouseMove(MouseEvent event) {
        if(pressed) {
            pressedCoordinates.set(event.getX(), event.getY());
        } else {
            movingCoordinates.set(event.getX(), event.getY());
        }

        //
    }
}
*/
