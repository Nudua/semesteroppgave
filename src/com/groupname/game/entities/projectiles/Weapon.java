package com.groupname.game.entities.projectiles;


import com.groupname.framework.audio.SoundPlayer;
import com.groupname.framework.collision.BoundsChecker;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.core.UpdateDrawAble;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.data.AppSettings;
import com.groupname.game.entities.Actor;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.SpriteFactory;
import javafx.scene.shape.Rectangle;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;


public interface Weapon extends UpdateDrawAble {

    void fire(Vector2D startPosition);

    void setDirection(Direction direction);



    //void checkCollision(List<Enemy> enemies);
}


