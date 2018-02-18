package com.groupname.framework.graphics.background;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteOld;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;

import java.util.List;
import java.util.Objects;

public class Star extends GameObject {

    public enum StarType {
        Small,
        Medium,
        Big
    }

    private final static double SPEED_SLOW = 1.4d;
    private final static double SPEED_MEDIUM = 5.0d;
    private final static double SPEED_FAST = 20.0d;

    private Size screenBounds;
    private StarType starType;

    private final double speed;

    public Star(Sprite sprite, Vector2D position, Size screenBounds, StarType starType) {
        super(sprite, position);

        this.screenBounds = Objects.requireNonNull(screenBounds);
        this.starType = Objects.requireNonNull(starType);

        speed = getStarSpeedFromType();
    }

    public static Sprite getSpriteFromType(StarType starType, List<Sprite> sprites) {
        switch(starType) {
            case Small:
                return sprites.get(0);
            case Medium:
                return sprites.get(1);
            case Big:
                return sprites.get(2);
            default:
                return sprites.get(0);
        }
    }

    private double getStarSpeedFromType() {
        switch(starType) {
            case Small:
                return SPEED_SLOW;
            case Medium:
                return SPEED_MEDIUM;
            case Big:
                return SPEED_FAST;
                default:
                    return SPEED_SLOW;
        }
    }

    // Star logic
    public void update() {
        // Just moving from right to left
        //Left
        position.addX(-speed);

        if(position.getX() <= 0) {
            position.setX(screenBounds.getWidth() + sprite.getWidth());
        }


        // Right
        /*
        position.addX(speed);

        if(position.getX() > screenBounds.getWidth() + sprite.getWidth()) {
            position.setX(position.getX() - screenBounds.getWidth());
        }
        */
    }

    public void draw(SpriteBatch spriteBatch) {

        spriteBatch.draw(sprite, position);
    }
}