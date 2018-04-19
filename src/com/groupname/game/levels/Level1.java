package com.groupname.game.levels;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.animation.AnimationFrame;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.core.Game;
import com.groupname.game.core.LevelMetaData;
import com.groupname.game.data.AppSettings;
import com.groupname.game.editor.metadata.LevelFactory;
import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.EnemySpriteType;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SpriteFactory;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.entities.enemies.HomingEnemy;
import com.groupname.game.entities.enemies.TowerEnemy;
import com.groupname.game.entities.powerups.HeartPowerUp;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.levels.core.LevelState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Level1 extends LevelBase {

    private Player player;
    public Level1(Game game, GraphicsContext graphicsContext) {
        super(game, graphicsContext);

        backgroundColor = Color.CORNFLOWERBLUE;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void reset() {
        super.reset();

        gameObjects.clear();

        initialize();
    }

    public void initialize() {
        createSpriteSheets();

        createPlayer1();
        createEnemy1();

    }

    private void createSpriteSheets() {
        Image playerSheet = Content.loadImage("player1.png", ResourceType.SpriteSheet);
        Image enemySheet = Content.loadImage("projectiles.png", ResourceType.SpriteSheet);

        addSpriteSheet(new SpriteSheet("player1", playerSheet));
        addSpriteSheet(new SpriteSheet("enemy1", enemySheet));
    }

    private void createPlayer1() {
        Sprite p1Sprite = new Sprite(getSpriteSheet("player1"), Sprite.createSpriteRegion(160, 160));
        p1Sprite.setScale(0.5d);
        player = new Player(p1Sprite, new Vector2D(800,200), inputManager);
        player.setOnDeath(this::gameOver);
        gameObjects.add(player);
    }

    private void gameOver() {
        //reset();
        //SceneManager.INSTANCE.changeToScene(SceneName.GameOver);
        state = LevelState.GameOver;
    }

    private void createEnemy1() {

        SpriteSheet sp1 = new SpriteSheet("spritesheet1", Content.loadImage("spritesheet1.png", ResourceType.SpriteSheet));

        int delay = 10;
        AnimationFrame eframe1 = new AnimationFrame(Sprite.createSpriteRegion(0,0,66,66), delay);
        AnimationFrame eframe2 = new AnimationFrame(Sprite.createSpriteRegion(1,0,66,66), delay);
        AnimationFrame eframe3 = new AnimationFrame(Sprite.createSpriteRegion(0,1,66,66), delay);
        AnimationFrame eframe4 = new AnimationFrame(Sprite.createSpriteRegion(1,1,66,66), delay);

        AnimationFrame eframe5 = new AnimationFrame(Sprite.createSpriteRegion(0,0,66,66), 20);
        AnimationFrame eframe6 = new AnimationFrame(Sprite.createSpriteRegion(0,1,66,66), 20);

        AnimatedSprite enemySprite = new AnimatedSprite(getSpriteSheet("enemy1"), eframe1.getSpriteRegion(), Arrays.asList(eframe5, eframe2 , eframe6));
        AnimatedSprite enemy2Sprite = new AnimatedSprite(getSpriteSheet("enemy1"), eframe1.getSpriteRegion(), Arrays.asList(eframe1, eframe2));
        AnimatedSprite enemy3Sprite = new AnimatedSprite(getSpriteSheet("enemy1"), eframe1.getSpriteRegion(), Arrays.asList(eframe4));
        enemy3Sprite.setScale(0.5d);
        enemy2Sprite.setScale(2.0d);

        SpriteFactory spriteFactory = new SpriteFactory();


        HomingEnemy enemy4 = new HomingEnemy(enemySprite, new Vector2D(900, 50), player);
        enemy4.setSpeed(0.1d);
        HomingEnemy enemy5 = new HomingEnemy(enemySprite, new Vector2D(300, 500), player);
        enemy5.setSpeed(0.5d);
        HomingEnemy enemy6 = new HomingEnemy(enemySprite, new Vector2D(100, 700), player);
        enemy6.setSpeed(2.5d);

        TowerEnemy enemy7 = new TowerEnemy(spriteFactory.createEnemy(EnemySpriteType.Snail), new Vector2D(600,300), Direction.Up, player);

        //gameObjects.addAll(Arrays.asList(enemy, enemy2, enemy3));
        gameObjects.addAll(Arrays.asList(enemy7));

        delay = 10;

        AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(0,0,64,64), delay);
        AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(1,0,64,64), delay);
        AnimationFrame frame3 = new AnimationFrame(Sprite.createSpriteRegion(0,1,64,64), delay);
        AnimationFrame frame4 = new AnimationFrame(Sprite.createSpriteRegion(1,1,64,64), delay);

        AnimatedSprite heartSprite = new AnimatedSprite(sp1, frame1.getSpriteRegion(), Arrays.asList(frame1, frame2, frame3, frame4));

        gameObjects.add(new HeartPowerUp(heartSprite, new Vector2D(500, 500), 1));


    }

    public void update() {
        // Update input to the most recent state
        /*
        if(inputManager.isEnabled()) {
            inputManager.update();
        }
        */

        boolean anyEnemyAlive = false;
        // Update all our gameobjects
        for(GameObject gameObject : gameObjects) {
            gameObject.update();

            if(gameObject instanceof Player) {
                Player player2 = (Player)gameObject;

                player2.checkCollision(gameObjects);
            }

            if(gameObject instanceof Enemy && !anyEnemyAlive) {
                Enemy enemy = (Enemy)gameObject;
                if(enemy.isAlive()) {
                    anyEnemyAlive = true;
                }
            }
        }

        if(!anyEnemyAlive) {
            state = LevelState.Completed;
            //SceneManager.INSTANCE.changeToScene(SceneName.Credits);
        }
    }

    public void draw() {
        clearScreen();


        // We should consider creating a class for drawing text
        graphicsContext.setFill(Color.BLACK);
        //graphicsContext.fillText("Use the arrow keys to move the player object!", 100, 100);

        // Draw all our gameobjects, really need to sort based on sprite priority
        for(GameObject gameObject : gameObjects) {
            gameObject.draw(spriteBatch);
        }
        graphicsContext.setFont(Font.font(26));
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText(String.format("HP: %d", player.getHitPoints()), 5, 30);
    }
}
