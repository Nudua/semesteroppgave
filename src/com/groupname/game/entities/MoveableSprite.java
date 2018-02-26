package com.groupname.game.entities;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;

public class MoveableSprite extends GameObject {

    public MoveableSprite(Sprite sprite, Vector2D startPosition) {
        super(sprite, startPosition);

        enabled = true;
    }

    @Override
    public void update() {
        // Don't update if this object isn't enabled
        if(!enabled) {
            return;
        }

        // Logic goes here

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
