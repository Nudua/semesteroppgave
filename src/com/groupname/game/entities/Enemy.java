package com.groupname.game.entities;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteFlip;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Actor;
import com.groupname.game.entities.projectiles.Projectile;
import com.groupname.game.entities.projectiles.SingleBulletWeapon;
import com.groupname.game.entities.projectiles.Weapon;
import com.groupname.game.input.PlayerInputDefinitions;

import java.util.Objects;


public abstract class Enemy extends Actor {
    public Enemy(Sprite sprite, Vector2D position) {
        super(sprite, position);
    }

    // Enemy helper methods here
}
