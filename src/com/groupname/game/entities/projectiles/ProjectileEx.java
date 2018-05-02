package com.groupname.game.entities.projectiles;

import com.groupname.framework.collision.BoundsChecker;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.data.AppSettings;
import javafx.scene.shape.Rectangle;

public class ProjectileEx extends GameObject {
    private boolean alive = false;
    private static final Rectangle SCREEN_BOUNDS = new Rectangle(AppSettings.SCREEN_BOUNDS.getWidth(), AppSettings.SCREEN_BOUNDS.getHeight());
    private final BoundsChecker boundsChecker;

    public ProjectileEx(Sprite sprite) {
        super(sprite, new Vector2D());
        boundsChecker = new BoundsChecker();
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void update() {
        if(isAlive()) {
            // Update our sprite if it's animated
            AnimatedSprite.stepIfAnimatedSprite(sprite);
        }

        // Check that our projectile is still within the screen.
        alive = boundsChecker.isWithinBounds(this, SCREEN_BOUNDS);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(isAlive()) {
            spriteBatch.draw(sprite, position);
        }
    }
}
