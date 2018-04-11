package com.groupname.game.editor.metadata;


import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.level.Tile;
import com.groupname.framework.level.TilePattern;
import com.groupname.framework.level.TileType;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.entities.powerups.HeartPowerUp;
import com.groupname.game.entities.powerups.PowerUp;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.security.InvalidParameterException;
import java.util.*;


public class LevelFactory {

    private final Map<String,SpriteSheet> spriteSheets;
    private final InputManager inputManager;

    public LevelFactory(InputManager inputManager) {
        spriteSheets = new HashMap<>();
        this.inputManager = Objects.requireNonNull(inputManager);
    }

    public void initialize() {
        createSpriteSheets();
    }

    private void createSpriteSheets() {
        Image playerSheet = Content.loadImage("player1.png", ResourceType.SpriteSheet);
        Image enemySheet = Content.loadImage("projectiles.png", ResourceType.SpriteSheet);

        spriteSheets.put("player1", new SpriteSheet("player1", playerSheet));
        spriteSheets.put("enemy1", new SpriteSheet("enemy1", enemySheet));
    }

    public GameObject create(ObjectMetaData metaData) {
        if(metaData.getType() == Player.class) {
            return createPlayer(metaData);
        } else if(Enemy.class.isAssignableFrom(metaData.getType())) { // is subclass of Enemy
            return createEnemy(metaData);
        } else if(PowerUp.class.isAssignableFrom(metaData.getType())) {
            return createPowerUp(metaData);
        } else if(metaData.getType() == TileType.class) {
            return createTile(metaData);
        }

        throw new InvalidParameterException("Unsupported object");
    }

    private Tile createTile(ObjectMetaData metaData) {
        if(!(metaData instanceof TileMetaData)) {
            throw new InvalidParameterException(); // Make custom exception
        }

        TileMetaData tileMetaData = (TileMetaData)metaData;

        Rectangle spriteRegion = Sprite.createSpriteRegion(0, 0, Tile.Size, Tile.Size);

        if(tileMetaData.getTilePattern() == TilePattern.Wall) {
            spriteRegion = Sprite.createSpriteRegion(0,1, Tile.Size, Tile.Size);
        }

        Sprite texture = new Sprite(spriteSheets.get("enemy1"), spriteRegion);

        return new Tile(texture, tileMetaData.getPosition(), tileMetaData.getTileType());
    }

    /**
     * Player(s)
     */
    private Player createPlayer(ObjectMetaData levelObject) {
        Sprite p1Sprite = new Sprite(spriteSheets.get("player1"), Sprite.createSpriteRegion(160, 160));
        p1Sprite.setScale(0.5d);
        return new Player(p1Sprite, levelObject.getPosition(), inputManager, 5);
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
        Sprite sprite = new Sprite(spriteSheets.get("enemy1"), Sprite.createSpriteRegion(0, 1, 66, 66));
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
        }

        throw new InvalidParameterException("Unsupported object");
    }

    private GuardEnemy createGuardEnemy(EnemyMetaData metaData) {
        Sprite sprite = new Sprite(spriteSheets.get("enemy1"), Sprite.createSpriteRegion(66, 66));

        int hitPoints;
        double speed;

        switch (metaData.getDifficulty()) {
            default:
            case Easy:
                hitPoints = 3;
                speed = 10;
                break;
            case Medium:
                hitPoints = 5;
                speed = 20;
                break;
            case Hard:
                hitPoints = 10;
                speed = 30;
                break;
            case Impossible:
                hitPoints = 20;
                speed = 30;
                break;
        }

        GuardEnemy enemy = new GuardEnemy(sprite, metaData.getPosition(), hitPoints);
        enemy.setSpeed(speed);

        return enemy;
    }



}
