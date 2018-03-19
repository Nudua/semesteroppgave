package com.groupname.game.entities.projectiles;


import com.groupname.framework.core.UpdateDrawAble;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Actor;

import java.util.List;


public interface Weapon extends UpdateDrawAble {
    String NAME = "GenericWeapon";

    boolean isEnabled();
    void setEnabled(boolean enabled);

    void fire(Vector2D startPosition, Direction direction);

    void checkCollision(List<Actor> enemies);
}


