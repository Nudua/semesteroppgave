package com.groupname.game.editor.metadata;


import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.animation.AnimationFrame;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.level.Tile;
import com.groupname.framework.level.TilePattern;
import com.groupname.framework.level.TileType;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.EnemySpriteType;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SpriteFactory;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.entities.enemies.HomingEnemy;
import com.groupname.game.entities.enemies.TowerEnemy;
import com.groupname.game.entities.powerups.HeartPowerUp;
import com.groupname.game.entities.powerups.PowerUp;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.security.InvalidParameterException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class LevelFactory {

    private final InputManager inputManager;
    private final SpriteFactory spriteFactory;
    private Player player;

    public LevelFactory(InputManager inputManager) {
        this.inputManager = Objects.requireNonNull(inputManager);
        this.spriteFactory = new SpriteFactory();
    }

    public void setPlayer(Player player) {
        this.player = Objects.requireNonNull(player);
    }

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

        //Sprite p1Sprite = new Sprite(spriteSheets.get("player1"), Sprite.createSpriteRegion(2,0, 124, 124));
        sprite.setScale(0.85d);
        return new Player(sprite, metaData.getPosition(), inputManager);
    }

    /**
     * PowerUps
     */
    private PowerUp createPowerUp(ObjectMetaData metaData) {
        if(!(metaData instanceof PowerUpMetaData)) {
            throw new InvalidParameterException(); // Make custom exception
        }

        PowerUpMetaData powerUpMetaData = (PowerUpMetaData)metaData;

        if(powerUpMetaData.getType() == HeartPowerUp.class) {
            return createHeartPowerUp(powerUpMetaData);
        }

        throw new InvalidParameterException("Unsupported object");
    }

    private HeartPowerUp createHeartPowerUp(PowerUpMetaData metaData) {
        Sprite sprite = spriteFactory.createEnemy(EnemySpriteType.Jellyfish);// new Sprite(spriteSheets.get("enemies"), Sprite.createSpriteRegion(0, 1, 66, 66));
        return new HeartPowerUp(sprite, metaData.getPosition(), metaData.getAmount());
    }

    /**
     * Enemies
     */
    private Enemy createEnemy(ObjectMetaData levelObject) {
        if(!(levelObject instanceof EnemyMetaData)) {
            throw new InvalidParameterException(); // Make custom exception
        }

        EnemyMetaData enemyMetaData = (EnemyMetaData)levelObject;

        if(enemyMetaData.getType() == GuardEnemy.class) {
            return createGuardEnemy(enemyMetaData);
        } else if(enemyMetaData.getType() == HomingEnemy.class) {
            return createHomingEnemy(enemyMetaData);
        } else if(enemyMetaData.getType() == TowerEnemy.class) {
            return createTowerEnemy(enemyMetaData);
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

        return enemy;
    }

    private void setDifficulty(EnemyMetaData metaData, Enemy enemy) {
        int hitPoints;
        double speed;
        int frequency;
        ThreadLocalRandom random = ThreadLocalRandom.current();

        switch (metaData.getDifficulty()) {
            default:
            case Easy:
                hitPoints = random.nextInt(1,3);
                speed = random.nextDouble(0.5d, 3d);
                frequency = random.nextInt(5,7);
                break;
            case Medium:
                hitPoints = random.nextInt(3,5);
                speed = random.nextDouble(3d,4.5d);
                frequency = random.nextInt(4,6);
                break;
            case Hard:
                hitPoints = random.nextInt(7,11);
                speed = random.nextDouble(5d, 7d);
                frequency = random.nextInt(3,5);
                break;
            case Impossible:
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
}
