package com.groupname.game.entities;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.math.Vector2D;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import javafx.scene.shape.Rectangle;

public class SimpleGameObject extends GameObject {

    private Rectangle screenBounds;

    // Logic
    private double speed;
    private boolean goRight;
    private boolean goUp;

    public SimpleGameObject(Sprite sprite, Vector2D position, Rectangle screenBounds, double speed, boolean goRight) {
        super(sprite, position);
        this.screenBounds = screenBounds;

        this.speed = speed;
        this.goRight = goRight;
        goUp = false;

        enabled = true;
    }

    @Override
    public void update() {
        // Don't update if this object isn't enabled
        if(!enabled) {
            return;
        }

        int width = (int)screenBounds.getWidth();
        int height = (int)screenBounds.getHeight();

        //right = true;

        // Bounce if we're outside the bounds of the screen on the x-axis
        if(position.getX() >= width - sprite.getWidth()) {
            goRight = false;
        }
        else if(position.getX() <= 0) {
            goRight = true;
        }

        // Bounce if we're outside the bounds of the screen on the y-axis
        if(position.getY() >= height - sprite.getHeight()) {
            goUp = true;
        }
        else if(position.getY() <= 0) {
            goUp = false;
        }

        double actualSpeed = goRight ? speed : -speed;

        double actualSpeedY = goUp ? -speed : speed;

        // add velocity to the spriteOld
        position.addX(actualSpeed);
        position.addY(actualSpeedY);

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
