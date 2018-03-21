package com.groupname.framework.level;

import com.groupname.framework.core.DrawAble;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.IntVector2D;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

enum TileType {
    Solid,
    UnSolid
}

public class Tile implements DrawAble {

    private final Sprite texture;
    private final TileType tileType;
    private final Vector2D position;

    public Tile(Sprite texture, IntVector2D smallPosition, Size size, TileType tileType) {
        this.texture = Objects.requireNonNull(texture);
        this.tileType = Objects.requireNonNull(tileType);

        position = new Vector2D(smallPosition.getX() * size.getWidth(), smallPosition.getY() * size.getHeight());
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, position);
    }

    public TileType getTileType() {
        return tileType;
    }
}
