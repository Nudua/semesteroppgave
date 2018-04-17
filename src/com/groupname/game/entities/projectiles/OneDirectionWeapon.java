package com.groupname.game.entities.projectiles;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.animation.AnimationFrame;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.SpriteFactory;
import javafx.scene.image.Image;

import java.util.Arrays;

public class OneDirectionWeapon {
    private static final String NAME = "Tower Weapon!";
    private final SpriteFactory spriteFactory;
    private final double speed = 20d;
    private final int damage = 1;
    private Projectile projectile;
    private Direction direction = Direction.Up;

    public OneDirectionWeapon() {
        this.spriteFactory = new SpriteFactory();
        Sprite sprite = spriteFactory.createProsjectile();
        projectile = new Projectile(sprite);
    }


    public void fire(Vector2D startPosition) {
        if(!projectile.isAlive()) {
            projectile.setPosition(startPosition);
            projectile.setDirection(direction);
            projectile.setAlive(true);
        }
    }

    public void checkCollision() {

    }

    public void update() {
        Vector2D position = projectile.getPosition();
        Direction direction = projectile.getDirection();
        Size screenBounds = new Size(1280, 720);

        double currentSpeed = speed;

        if(direction == Direction.Right || direction == Direction.Down) {
            currentSpeed = speed;
        } else if(direction == Direction.Left || direction == Direction.Up) {
            currentSpeed = -speed;
        }

        if(direction == Direction.Up || direction == Direction.Down) {
            position.addY(currentSpeed);
        } else {
            position.addX(currentSpeed);
        }

        //X-axis
        if(position.getX() + projectile.getSprite().getWidth() > screenBounds.getWidth() || position.getX() < 0) {
            projectile.setAlive(false);
        }

        //Y-axis
        if(position.getY() < 0 || position.getY() + projectile.getSprite().getHeight() > screenBounds.getHeight()) {
            projectile.setAlive(false);
        }

        projectile.update();

    }

    public void draw(SpriteBatch spriteBatch) {
        projectile.draw(spriteBatch);
    }
}
