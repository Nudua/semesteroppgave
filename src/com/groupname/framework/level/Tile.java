package com.groupname.framework.level;

import com.groupname.framework.core.DrawAble;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.IntVector2D;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import javafx.scene.shape.Rectangle;

import java.util.Objects;



public class Tile extends GameObject {

    public static final int Size = 64;
    private final TileType tileType;

    public Tile(Sprite sprite, Vector2D position, TileType tileType) {
        super(sprite, position);
        this.tileType = Objects.requireNonNull(tileType);
    }

    public TileType getTileType() {
        return tileType;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(sprite, position);
    }

    @Override
    public void update() {
        AnimatedSprite.stepIfAnimatedSprite(sprite);
    }
}
