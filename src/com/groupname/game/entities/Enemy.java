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


public abstract class Enemy extends Actor {
    private double speed = 2.5d;
    private final Vector2D initialPosition;

    protected SpriteFlip spriteFlip = SpriteFlip.NONE;

    public Enemy(Sprite sprite, Vector2D position, int hitPoints) {
        super(sprite, position, hitPoints);
        initialPosition = new Vector2D(position);
    }

}
