package com.groupname.framework.graphics.background.gameover;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;

public class WeatherFlake extends GameObject {

    public final static double DEFAULT_OSCILATESPEED = 0.4d;
    public final static double DEFAULT_FALL_SPEED = 1.8d;

    private Size screenBounds;
    private double fallSpeed = DEFAULT_FALL_SPEED;
    private double ossilateCurrentSpeed = 0;
    private double ossilateAccel = 0.05;

    private boolean goRight;
    private int timer;

    private int stallTimer = 0;

    public WeatherFlake(Sprite sprite, Vector2D position, Size screenBounds, boolean goRight, int stallTimer) {
        super(sprite, position);
        this.goRight = goRight;
        this.screenBounds = screenBounds;
        this.stallTimer = stallTimer;
    }

    public void update() {

        if(stallTimer > 0) {
            stallTimer--;
            return;
        }

        timer++;

        position.addY(fallSpeed);

        if (position.getY() > screenBounds.getHeight()) {
            position.setY(position.getY() - screenBounds.getHeight() - sprite.getHeight());
        }

        if(ossilateCurrentSpeed < DEFAULT_OSCILATESPEED) {
            ossilateCurrentSpeed+= ossilateAccel;
        }

        if(timer > 60) {
            timer = 0;
            goRight = !goRight;
            ossilateCurrentSpeed = 0;
        }

        double speed = goRight ? ossilateCurrentSpeed : -ossilateCurrentSpeed;

        position.addX(speed);
    }

    public void draw(SpriteBatch spriteBatch) {
        if(stallTimer > 0) {
            return;
        }

        spriteBatch.draw(sprite, position);
    }
}