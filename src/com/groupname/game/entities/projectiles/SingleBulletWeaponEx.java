package com.groupname.game.entities.projectiles;

import com.groupname.framework.audio.SoundPlayer;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;

public class SingleBulletWeaponEx extends WeaponBase {

    private Direction bulletDirection = Direction.None;

    public SingleBulletWeaponEx(double speed, int damage) {
        super(speed, damage);
    }

    @Override
    public void fire(Vector2D startPosition, Direction direction) {
        ProjectileEx myOnlyBullet = projectiles.get(0);

        this.bulletDirection = direction;

        if(!myOnlyBullet.isAlive()) {

            System.out.println("Shooting");

            SoundPlayer.INSTANCE.playSoundEffect(SoundPlayer.SoundEffect.Shoot);
            myOnlyBullet.setPosition(startPosition);
            myOnlyBullet.setAlive(true);
        }
    }

    @Override
    protected void updateProjectileLogic() {
        ProjectileEx myOnlyBullet = projectiles.get(0);

        Vector2D position = myOnlyBullet.getPosition();
        Size screenBounds = new Size(1280, 720);

        double currentSpeed = speed;

        if(bulletDirection == Direction.Right || bulletDirection == Direction.Down) {
            currentSpeed = speed;
        } else if(bulletDirection == Direction.Left || bulletDirection == Direction.Up) {
            currentSpeed = -speed;
        }

        if(bulletDirection == Direction.Up || bulletDirection == Direction.Down) {
            position.addY(currentSpeed);
        } else {
            position.addX(currentSpeed);
        }

        //X-axis
        if(position.getX() + myOnlyBullet.getSprite().getWidth() > screenBounds.getWidth() || position.getX() < 0) {
            myOnlyBullet.setAlive(false);
        }

        //Y-axis
        if(position.getY() < 0 || position.getY() + myOnlyBullet.getSprite().getHeight() > screenBounds.getHeight()) {
            myOnlyBullet.setAlive(false);
        }

        myOnlyBullet.setPosition(position);

        myOnlyBullet.update();
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        ProjectileEx myOnlyBullet = projectiles.get(0);
        myOnlyBullet.draw(spriteBatch);
    }

    @Override
    public void update() {
        updateProjectileLogic();
    }
}
