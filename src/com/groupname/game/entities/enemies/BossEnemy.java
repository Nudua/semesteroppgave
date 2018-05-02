package com.groupname.game.entities.enemies;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Counter;
import com.groupname.framework.math.Vector2D;
import com.groupname.framework.collision.BoundsChecker;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;
import javafx.scene.shape.Rectangle;


import java.util.concurrent.ThreadLocalRandom;

/**
 * This class extends Enemy. BossEnemy is an enemy that moves slowly, shoots a little and is hard to kill.
 */
public class BossEnemy extends Enemy {
    //private SpreadGun currentWeapon;
    private Player player;
    private Vector2D basePosition;
    private Rectangle bossBounds;
    private BoundsChecker boundsChecker = new BoundsChecker();
    private Counter counter = new Counter(5);
    private Counter counterInAction = new Counter(2);



    /**
     * The constructor for an BossEnemy. Takes a sprite, start position and the player.
     *
     * @param sprite Sets an default sprite for the enemy.
     * @param position Sets the start position, on the canvas.
     * @param player takes the player for accessing the players position.
     */
    public BossEnemy(Sprite sprite, Vector2D position, Player player) {
        super(sprite, position);
        this.player = player;
        this.basePosition = position;
        createBossBounds();
        sprite.setScale(4.0d);

    }

    private void createBossBounds() {
        double width = sprite.getWidth() + 400;
        double height = sprite.getHeight() + 200;
        bossBounds = new Rectangle((int)basePosition.getX() - 300, (int)basePosition.getY() - 100, (int)width, (int)height);
    }

    public Rectangle getBossBounds() {
        return bossBounds;
    }

    @Override
    public Rectangle getHitbox() {
        double scale = sprite.getScale();
        return new Rectangle(position.getX() + 6 * scale, position.getY() + 40 * scale, sprite.getWidth() - 12 * scale, sprite.getHeight() - 40 * scale);
    }


    /**
     * The specific logic for this type of enemy.
     * The BossEnemy continuous moves a little bit, and at some random times it takes a leap against the player.
     */
    @Override
    public void update() {
        super.update();
        double randomX = ThreadLocalRandom.current().nextDouble(5d, 10d);
        double randomY = ThreadLocalRandom.current().nextDouble(-1.1d, 1.1d);

        counter.step();

        if(counter.isDone()){
            if(counterInAction.getCounter() < 60) {
                position.addX(-randomX);
                position.addY(randomY);
                System.out.println(randomX);
            } if(counterInAction.getCounter() > 60) {
                position.addX(randomX);
                position.addY(randomY);
                System.out.println("-"+randomX);
            }
            counterInAction.step();
            if(counterInAction.isDone()){
                counter.reset();
                counterInAction.reset();
            }
        } else {

            if(boundsChecker.isWithinBounds(this, bossBounds)){
                position.add(randomX, randomY);
            }
            if(position.getX() + sprite.getWidth() >= bossBounds.getX() + bossBounds.getWidth()){
                position.addX(-20);
            }
            if(position.getX() <= bossBounds.getX()){
                position.addX(20);
            }
            if(position.getY() + sprite.getHeight() >= bossBounds.getY() + bossBounds.getHeight()) {
                position.addY(-20);
            }
            if(position.getY() <= bossBounds.getY()){
                position.addY(20);
            }

        }


    }

    /**
     * Draws the enemy if it is alive.
     *
     * @param spriteBatch draws the given sprite at the specified position
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(isAlive()) {
            spriteBatch.draw(sprite, position);
            //currentWeapon.draw(spriteBatch);
        }

    }

    /**
     * Method for resetting of the enemy.
     */
    @Override
    public void reset() {

    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "BossEnemy{" +
                "player=" + player +
                ", basePosition=" + basePosition +
                ", bossBounds=" + bossBounds +
                ", boundsChecker=" + boundsChecker +
                ", counter=" + counter +
                ", counterInAction=" + counterInAction +
                '}';
    }
}
