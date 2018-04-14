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
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.entities.enemies.HomingEnemy;
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
        Image playerSheet = Content.loadImage("alien-works.png", ResourceType.SpriteSheet);
        Image enemySheet = Content.loadImage("enemies.png", ResourceType.SpriteSheet);

        spriteSheets.put("player1", new SpriteSheet("player1", playerSheet));
        spriteSheets.put("enemies", new SpriteSheet("enemies", enemySheet));
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

        Sprite texture = new Sprite(spriteSheets.get("enemies"), spriteRegion);

        return new Tile(texture, tileMetaData.getPosition(), tileMetaData.getTileType());
    }

    /**
     * Player(s)
     */
    private Player createPlayer(ObjectMetaData levelObject) {

        // Idle animation, player class need a lot of work for animations!
        AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(0,0, 124, 124), 60 * 2);
        AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(1,0, 124, 124), 60);
        AnimationFrame frame3 = new AnimationFrame(Sprite.createSpriteRegion(2,0, 124, 124), 60);
        AnimationFrame frame4 = new AnimationFrame(Sprite.createSpriteRegion(1,0, 124, 124), 60);

        AnimatedSprite animatedSprite = new AnimatedSprite(spriteSheets.get("player1"), frame1.getSpriteRegion(), Arrays.asList(frame1, frame2, frame3, frame4));

        //Sprite p1Sprite = new Sprite(spriteSheets.get("player1"), Sprite.createSpriteRegion(2,0, 124, 124));
        animatedSprite.setScale(0.85d);
        return new Player(animatedSprite, levelObject.getPosition(), inputManager, 5);
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
        Sprite sprite = new Sprite(spriteSheets.get("enemies"), Sprite.createSpriteRegion(0, 1, 66, 66));
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

        Sprite sprite;

        if(metaData.getSpriteType() == EnemySpriteType.Blob) {
            AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(0, 0, 80, 80), 20);
            AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(1, 0, 80, 80), 20);
            sprite = new AnimatedSprite(spriteSheets.get("enemies"), frame1.getSpriteRegion(), Arrays.asList(frame1, frame2));
        } else { // Fly for now
            AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(0, 3, 80, 80), 10);
            AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(1, 3, 80, 80), 10);
            sprite = new AnimatedSprite(spriteSheets.get("enemies"), frame1.getSpriteRegion(), Arrays.asList(frame1, frame2));
        }

        int hitPoints;
        double speed;

        switch (metaData.getDifficulty()) {
            default:
            case Easy:
                hitPoints = 3;
                speed = 3;
                break;
            case Medium:
                hitPoints = 5;
                speed = 7;
                break;
            case Hard:
                hitPoints = 10;
                speed = 10;
                break;
            case Impossible:
                hitPoints = 20;
                speed = 15;
                break;
        }

        GuardEnemy enemy = new GuardEnemy(sprite, metaData.getPosition(), hitPoints);
        enemy.setSpeed(speed);

        return enemy;
    }
}
