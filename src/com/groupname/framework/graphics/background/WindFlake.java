package com.groupname.framework.graphics.background;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteOld;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;

public class WindFlake extends GameObject {

    public final static double MAX_WIND_SPEED = 7.0d;
    public final static double MIN_WIND_SPEED = 1.0d;

    private Size screenBounds;
    private double speed;

    private int timer;

    private int stallTimer = 0;

    private double fallspeed = 0.2d;

    public WindFlake(Sprite sprite, Vector2D position, Size screenBounds, int stallTimer, double speed) {
        super(sprite, position);
        this.screenBounds = screenBounds;
        this.stallTimer = stallTimer;
        this.speed = speed;
    }

    public void update() {

        if(stallTimer > 0) {
            stallTimer--;
            return;
        }

        timer++;

        position.addX(-speed);

        // Just wrap around
        if(position.getX() <= 0) {
            position.setX(screenBounds.getWidth() + sprite.getWidth());
        }

        position.addY(fallspeed);

        // Just wrap around
        if(position.getY() >= screenBounds.getHeight()) {
            position.setY(position.getY() - screenBounds.getHeight());
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        if(stallTimer > 0) {
            return;
        }

        spriteBatch.draw(sprite, position);
    }
}