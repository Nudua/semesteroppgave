package com.groupname.framework.input.devices;

import com.groupname.framework.math.Vector2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

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
