package com.groupname.game.editor.metadata;


import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.input.InputManager;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.EnemySpriteType;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SpriteFactory;
import com.groupname.game.entities.enemies.BossEnemy;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.entities.enemies.HomingEnemy;
import com.groupname.game.entities.enemies.TowerEnemy;
import com.groupname.game.entities.powerups.HeartPowerUp;
import com.groupname.game.entities.powerups.PowerUp;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Takes all the objects needed to create a level of it.
 */
public class LevelObjectFactory {

    private final InputManager inputManager;
    private final SpriteFactory spriteFactory;
    private Player player;

    /**
     * Creates a new instance.
     *
     * @param inputManager the inputManager to use.
     */
    public LevelObjectFactory(InputManager inputManager) {
        this.inputManager = Objects.requireNonNull(inputManager);
        this.spriteFactory = new SpriteFactory();
    }

    /**
     * Setter for the player.
     *
     * @param player the player to use.
     */
    public void setPlayer(Player player) {
        this.player = Objects.requireNonNull(player);
    }

    /**
     * Returns the created objects.
     *
     * @param metaData info about the objects.
     * @return the created objects.
     */
    public GameObject create(ObjectMetaData metaData) {
        if(metaData.getType() == Player.class) {
            return createPlayer(metaData);
        } else if(Enemy.class.isAssignableFrom(metaData.getType())) { // is subclass of Enemy
            return createEnemy(metaData);
        } else if(PowerUp.class.isAssignableFrom(metaData.getType())) {
            return createPowerUp(metaData);
        }

        throw new InvalidParameterException("Unsupported object");
    }

    /**
     * Player(s)
     */
    private Player createPlayer(ObjectMetaData metaData) {
        Sprite sprite = spriteFactory.createPlayer();

        sprite.setScale(0.85d);
        return new Player(sprite, metaData.getPosition(), inputManager);
    }

    /**
     * PowerUps
     */
    private PowerUp createPowerUp(ObjectMetaData metaData) {
        if(!(metaData instanceof PowerUpMetaData)) {
            throw new InvalidParameterException();
        }

        PowerUpMetaData powerUpMetaData = (PowerUpMetaData)metaData;

        if(powerUpMetaData.getType() == HeartPowerUp.class) {
            return createHeartPowerUp(powerUpMetaData);
        }

        throw new InvalidParameterException("Unsupported object");
    }

    private HeartPowerUp createHeartPowerUp(PowerUpMetaData metaData) {
        Sprite sprite = spriteFactory.createHeart()[0];
        return new HeartPowerUp(sprite, metaData.getPosition(), metaData.getAmount());
    }

    /**
     * Enemies
     */
    private Enemy createEnemy(ObjectMetaData levelObject) {
        if(!(levelObject instanceof EnemyMetaData)) {
            throw new InvalidParameterException();
        }

        EnemyMetaData enemyMetaData = (EnemyMetaData)levelObject;

        if(enemyMetaData.getType() == GuardEnemy.class) {
            return createGuardEnemy(enemyMetaData);
        } else if(enemyMetaData.getType() == HomingEnemy.class) {
            return createHomingEnemy(enemyMetaData);
        } else if(enemyMetaData.getType() == TowerEnemy.class) {
            return createTowerEnemy(enemyMetaData);
        } else if(enemyMetaData.getType() == BossEnemy.class) {
            return createBossEnemy(enemyMetaData);
        }

        throw new InvalidParameterException("Unsupported enemy");
    }

    private GuardEnemy createGuardEnemy(EnemyMetaData metaData) {
        Sprite sprite = spriteFactory.createEnemy(metaData.getSpriteType());

        GuardEnemy enemy = new GuardEnemy(sprite, metaData.getPosition());
        setDifficulty(metaData,enemy);

        return enemy;
    }

    private HomingEnemy createHomingEnemy(EnemyMetaData metaData) {
        Sprite sprite = spriteFactory.createEnemy(metaData.getSpriteType());

        HomingEnemy enemy = new HomingEnemy(sprite, metaData.getPosition(), player);
        setDifficulty(metaData, enemy);

        return enemy;
    }

    private TowerEnemy createTowerEnemy(EnemyMetaData metaData) {
        Sprite sprite = spriteFactory.createEnemy(metaData.getSpriteType());

        TowerEnemy enemy = new TowerEnemy(sprite, metaData.getPosition(), player);
        setDifficulty(metaData, enemy);

        return enemy;
    }

    private BossEnemy createBossEnemy(EnemyMetaData metaData) {
        Sprite sprite = spriteFactory.createEnemy(metaData.getSpriteType());

        BossEnemy enemy = new BossEnemy(sprite, metaData.getPosition(), player);
        enemy.setHitPoints(10);

        return enemy;
    }

    private void setDifficulty(EnemyMetaData metaData, Enemy enemy) {
        int hitPoints;
        double speed;
        int frequency;
        ThreadLocalRandom random = ThreadLocalRandom.current();

        switch (metaData.getDifficulty()) {
            default:
            case EASY:
                hitPoints = random.nextInt(1,3);
                speed = random.nextDouble(0.5d, 3d);
                frequency = random.nextInt(5,7);
                break;
            case MEDIUM:
                hitPoints = random.nextInt(3,5);
                speed = random.nextDouble(3d,4.5d);
                frequency = random.nextInt(4,6);
                break;
            case HARD:
                hitPoints = random.nextInt(7,11);
                speed = random.nextDouble(5d, 7d);
                frequency = random.nextInt(3,5);
                break;
            case IMPOSSIBLE:
                hitPoints = random.nextInt(11, 15);
                speed = random.nextDouble(7.5d, 15d);
                frequency = random.nextInt(1,3);
                break;
        }

        enemy.setHitPoints(hitPoints);
        enemy.setSpeed(speed);
        if(enemy instanceof HomingEnemy) {
            ((HomingEnemy) enemy).setFrequency(frequency);
        }
    }


    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "LevelObjectFactory{" +
                "inputManager=" + inputManager +
                ", spriteFactory=" + spriteFactory +
                ", player=" + player +
                '}';
    }
}
