package com.groupname.game.levels;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.animation.improved.AnimatedSprite;
import com.groupname.framework.graphics.animation.improved.AnimationFrame;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.entities.Actor;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.entities.powerups.HeartPowerUp;
import com.groupname.game.entities.powerups.PowerUp;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.levels.core.LevelState;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level1 extends LevelBase {

    public Level1(GameEngine game, InputManager inputManager) {
        super(game, inputManager);

        backgroundColor = Color.CORNFLOWERBLUE;
    }

    @Override
    public void reset() {
        super.reset();
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
        Sprite p1Sprite = new Sprite("player1Sprite", getSpriteSheet("player1"), Sprite.createSpriteRegion(160, 160));
        p1Sprite.setScale(0.5d);
        Player player = new Player(p1Sprite, new Vector2D(200,200), inputManager, 5);
        player.setOnDeath(() -> SceneManager.INSTANCE.changeToScene(SceneName.GameOver));
        gameObjects.add(player);
    }

    private void createEnemy1() {
        Sprite e1Sprite = new Sprite("enemy1Sprite", getSpriteSheet("enemy1"), Sprite.createSpriteRegion(66, 66));
        //e1Sprite.setScale(1.0d);
        Enemy enemy = new GuardEnemy(e1Sprite, new Vector2D(50,500), 3);

        gameObjects.add(enemy);

        SpriteSheet sp1 = new SpriteSheet("spritesheet1", Content.loadImage("spritesheet1.png", ResourceType.SpriteSheet));

        int delay = 10;

        AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(0,0,64,64), delay);
        AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(1,0,64,64), delay);
        AnimationFrame frame3 = new AnimationFrame(Sprite.createSpriteRegion(0,1,64,64), delay);
        AnimationFrame frame4 = new AnimationFrame(Sprite.createSpriteRegion(1,1,64,64), delay);

        AnimatedSprite heartSprite = new AnimatedSprite("Heart", sp1, Sprite.createSpriteRegion(64,64), Arrays.asList(frame1, frame2, frame3, frame4));

        gameObjects.add(new HeartPowerUp(heartSprite, new Vector2D(500, 500), 1));
    }

    public void update() {
        // Update input to the most recent state
        if(inputManager.isEnabled()) {
            inputManager.update();
        }

        // Update all our gameobjects
        for(GameObject gameObject : gameObjects) {
            gameObject.update();

            if(gameObject instanceof Player) {
                Player player = (Player)gameObject;

                player.checkCollision(gameObjects);
            }
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
    }
}
