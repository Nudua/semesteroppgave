package com.groupname.framework.core;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.math.Vector2D;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public abstract class GameObject implements UpdateDrawAble {

    protected boolean enabled;

    protected final Sprite sprite;
    protected final Vector2D position;

    public GameObject(Sprite sprite, Vector2D position) {
        this.sprite = Objects.requireNonNull(sprite, "sprite cannot be null");
        this.position = Objects.requireNonNull(position, "position cannot be null");
    }

    public Sprite getSprite() {
        return sprite;
    }

    // Not all gameobjects need a hitbox
    public Rectangle getHitbox() {
        return new Rectangle(position.getX(), position.getY(), sprite.getWidth(), sprite.getHeight());
    }

    // Simple bounding box collision detection
    public boolean collides(Rectangle with) {
        return getHitbox().intersects(with.getBoundsInParent());
    }

    public Vector2D getPosition() {
        return new Vector2D(position);
    }

    public void setPosition(Vector2D position) {
        this.position.set(position);
    }
    //public abstract void onCollides(int value);

    public abstract void update();
    public abstract void draw(SpriteBatch spriteBatch);
}
