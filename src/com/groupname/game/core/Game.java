package com.groupname.game.core;

import com.groupname.framework.math.Size;
import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.math.Vector2D;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.input.InputManager;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SimpleGameObject;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class Game extends GameEngine {

    private final SpriteBatch spritebatch;
    private final InputManager inputManager;

    private final List<GameObject> gameObjects;
    private Player player1;

    private GameObject box;

    public Game(GridPane parent, Scene scene, int width, int height) {
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
                new Sprite.Builder("box1", "sheet1",
                new Size(64,64)).
                sourceVector(0,0).build(),
                new Vector2D(200,200),
                new Rectangle(width, height), 4, true);

        gameObjects.add(box);
    }

    private void createPlayer1() {
        Sprite p1Sprite = new Sprite.Builder("player1sprite", "player1", new Size(160,160)).scale(0.5).build();

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
            player1.getSprite().setSourceVector(2,0);
            System.out.println("CRASH");
        } else {
            player1.getSprite().setSourceVector(0,0);
        }

        // Update all our gameobjects
        for(GameObject gameObject : gameObjects) {
            gameObject.update();
        }
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
    }
}
