package com.groupname.framework.input.devices;

import com.groupname.framework.math.Vector2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.security.InvalidParameterException;
import java.util.Objects;

interface TileEvent {
    void onAction(int x, int y);
}

// Figure out a better name
class MouseInputEx {

    private boolean pressed;
    private TileEvent onClicked;
    private TileEvent onMove;

    private final int tileSize;

    public MouseInputEx(Node scene, int tileSize) {
        scene.setOnMouseMoved(this::onMouseMove);
        scene.setOnMouseClicked(this::onMouseClicked);

        if(tileSize <= 0) {
            throw new InvalidParameterException("The tileSize cannot be zero or negative!");
        }

        this.tileSize = tileSize;
    }

    private void onMouseClicked(MouseEvent mouseEvent) {
        fireEventIfExists(onClicked, mouseEvent.getX(), mouseEvent.getY());
    }

    private void onMouseMove(MouseEvent mouseEvent) {
        fireEventIfExists(onMove, mouseEvent.getX(), mouseEvent.getY());
    }

    private void fireEventIfExists(TileEvent event, double x, double y) {
        if(event != null) {
            int smallX = (int)x / tileSize;
            int smallY = (int)y / tileSize;

            event.onAction(smallX, smallY);
        }
    }

    public void setOnClicked(TileEvent onClicked) {
        this.onClicked = onClicked;
    }

    public void setOnMove(TileEvent onHover) {
        this.onMove = onHover;
    }
}

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
