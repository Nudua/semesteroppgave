package com.groupname.game.core;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteOld;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.animation.LinearAnimation;
import com.groupname.framework.math.Size;
import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.math.Vector2D;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.input.InputManager;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SimpleGameObject;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class Game extends GameEngine {

    private final SpriteBatch spritebatch;
    private final InputManager inputManager;

    private final List<GameObject> gameObjects;
    private Player player1;

    private GameObject box;
    private AnimatedSprite animatedSprite;

    public Game(Pane parent, Scene scene, int width, int height) {
        super(parent, width, height);

        inputManager = new InputManager(scene);
        gameObjects = new ArrayList<>();
        spritebatch = new SpriteBatch(graphicsContext);

        loadResources();
    }

    private void loadResources() {
        createSpriteSheets();

        createBox();
        createPlayer1();
        createAnim();
    }

    private void createAnim() {

        Rectangle frame1 = Sprite.createSpriteRegion(0,0,64,64);
        Rectangle frame2 = Sprite.createSpriteRegion(1,0,64,64);
        Rectangle frame3 = Sprite.createSpriteRegion(0,1,64,64);
        Rectangle frame4 = Sprite.createSpriteRegion(1,1,64,64);
        Rectangle frame5 = Sprite.createSpriteRegion(0,2,64,64);
        Rectangle frame6 = Sprite.createSpriteRegion(1,2,64,64);

        animatedSprite = new AnimatedSprite("anim1", "sheet1", Arrays.asList(frame1, frame2, frame3, frame4, frame5, frame6));
        animatedSprite.setAnimationLogic(new LinearAnimation(30));
    }

    private void createSpriteSheets() {

        String spriteSheetFolder = "../resources/graphics/spritesheets/";

        Image sheet1 = new Image(getClass().getResourceAsStream(spriteSheetFolder + "spritesheet1.png"));
        Image playerSheet = new Image(getClass().getResourceAsStream(spriteSheetFolder + "player1.png"));

        spritebatch.addSpritesheet("sheet1", sheet1);
        spritebatch.addSpritesheet("player1", playerSheet);
    }

    private void createBox() {
        box = new SimpleGameObject(
                new Sprite("box1", "sheet1", Sprite.createSpriteRegion(64,64)),
                new Vector2D(200,200),
                new Size(width, height),
                4,
                true);

        gameObjects.add(box);
    }

    private void createPlayer1() {
        Sprite p1Sprite = new Sprite("player1Sprite", "player1", Sprite.createSpriteRegion(160, 160));
        p1Sprite.setScale(0.5d);

        player1 = new Player(p1Sprite, new Vector2D(), new Rectangle(width, height), inputManager);

        gameObjects.add(player1);
    }

    protected void update() {
        // Update input to the most recent state
        if(inputManager.isEnabled()) {
            inputManager.update();
        }

        // This should be handled by the objects itself, just to demonstrate simple collision detection
        if(box.collides(player1.getHitbox())) {
            player1.getSprite().setSpriteRegion(Sprite.createSpriteRegion(2,0,160,160));
            System.out.println("CRASH");
        } else {
            player1.getSprite().setSpriteRegion(Sprite.createSpriteRegion(0,0,160,160));
        }

        // Update all our gameobjects
        for(GameObject gameObject : gameObjects) {
            gameObject.update();
        }

        animatedSprite.stepAnimation();
    }

    protected void draw() {
        // Clear the entire screen with the default background color
        graphicsContext.setFill(background);
        graphicsContext.fillRect(0, 0, width, height);

        // We should consider creating a class for drawing text
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText("Use the arrow keys to move the player object!", 100, 100);

        // Draw all our gameobjects, really need to sort based on sprite priority
        for(GameObject gameObject : gameObjects) {
            gameObject.draw(spritebatch);
        }

        spritebatch.draw(animatedSprite, new Vector2D(200, 200));
    }
}
