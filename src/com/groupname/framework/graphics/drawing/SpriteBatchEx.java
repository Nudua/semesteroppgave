package com.groupname.framework.graphics.drawing;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.math.Vector2D;

import java.util.EnumSet;

public interface SpriteBatchEx {
    void draw(Sprite sprite, Vector2D position);
    void draw(Sprite sprite, Vector2D position, EnumSet<SpriteFlip> flipFlags);
}
