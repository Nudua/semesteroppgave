package com.groupname.game.entities;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.math.Vector2D;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteFlip;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.KeyboardInput;
import javafx.scene.shape.Rectangle;

import java.util.EnumSet;

// Simple example of a player controlled GameObject
public class Player extends GameObject {

    private final InputManager inputManager;
    private final Rectangle screenBounds;
    private boolean facingRight = false;

    // Logic
    private final double speed;

    public Player(Sprite sprite, Vector2D position, Rectangle screenBounds, InputManager inputManager) {
        super(sprite, position);
        this.screenBounds = screenBounds;
        this.inputManager = inputManager;

        speed = 8;

        enabled = true;
    }


    @Override
    public void update() {
        // Don't update if this object isn't enabled
        if(!enabled) {
            return;
        }

        // Very simple movement with bounds checking
        if(inputManager.isPressed(KeyboardInput.Defaults.RIGHT)) {
            if(position.getX() + sprite.getWidth() >= screenBounds.getWidth()) {
                position.setX(screenBounds.getWidth() - sprite.getWidth());
            } else {
                position.addX(speed);
            }

            facingRight = true;

        } else if(inputManager.isPressed(KeyboardInput.Defaults.LEFT)) {
            if(position.getX() > 0) {
                position.addX(-speed);
            } else {
                position.setX(0);
            }

            facingRight = false;
        }

        if(inputManager.isPressed(KeyboardInput.Defaults.UP)) {
            if(position.getY() <= 0) {
                position.setY(0);
            } else {
                position.addY(-speed);
            }

        } else if(inputManager.isPressed(KeyboardInput.Defaults.DOWN)) {
            if(position.getY() + sprite.getHeight() >= screenBounds.getHeight()) {
                position.setY(screenBounds.getHeight() - sprite.getHeight());
            }
            position.addY(speed);
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        // Don't draw if this object isn't enabled
        if(!enabled) {
            return;
        }

        EnumSet<SpriteFlip> flip = facingRight ? EnumSet.of(SpriteFlip.HORIZONTAL) : EnumSet.of(SpriteFlip.NONE);

        spriteBatch.draw(sprite, position, flip);
    }

}
