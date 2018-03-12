package com.groupname.game.other;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import javafx.scene.shape.Rectangle;

public class MoveableSprite extends GameObject {

    private boolean goRight = true;
    private Size screenBounds;
    private double speed = 10.5d;
    private InputManager inputManager;

    public MoveableSprite(Sprite sprite, Vector2D startPosition, Size screenBounds, InputManager inputManager) {
        super(sprite, startPosition);
        this.screenBounds = screenBounds;
        this.inputManager = inputManager;

        enabled = true;
    }

    @Override
    public void update() {
        // Don't update if this object isn't enabled
        if(!enabled) {
            return;
        }


        if(inputManager.isDown(KeyboardInput.Defaults.RIGHT)) {
            position.add(speed, 0);
        }

        if(inputManager.isDown(KeyboardInput.Defaults.SHOOT_RIGHT)) {
            position.add(-speed, 0);
        }

        Rectangle hitBox = getHitbox();

        Rectangle other = new Rectangle(0,0, 100, 100);

        if(hitBox.intersects(other.getBoundsInLocal())) {

        }

        /*
        if(goRight) {
            position.add(speed, 0);
        } else {
            position.add(-speed, 0);
        }

        // Logic goes here
        if(position.getX() >= screenBounds.getWidth()) {
            goRight = false;
        } else if(position.getX() < 0) {
            goRight = true;
        }
        */
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        // Don't draw if this object isn't enabled
        if(!enabled) {
            return;
        }

        spriteBatch.draw(sprite, position);
    }

}
