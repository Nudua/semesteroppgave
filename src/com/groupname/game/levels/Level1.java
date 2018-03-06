package com.groupname.game.levels;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.animation.LinearAnimation;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Actor;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SimpleGameObject;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.levels.core.LevelState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level1 extends LevelBase {

    private Actor player;

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
    }

    private void createSpriteSheets() {

        String spriteSheetFolder = "../resources/graphics/spritesheets/";

        Image playerSheet = new Image(getClass().getResourceAsStream(spriteSheetFolder + "player1.png"));

        addSpriteSheet(new SpriteSheet("player1", playerSheet));
    }

    private void createPlayer1() {
        Sprite p1Sprite = new Sprite("player1Sprite", getSpriteSheet("player1"), Sprite.createSpriteRegion(160, 160));
        p1Sprite.setScale(0.5d);
        player = new Player(p1Sprite, new Vector2D(200,200), 10, inputManager);
        gameObjects.add(player);
    }

    public void update() {
        // Update input to the most recent state
        if(inputManager.isEnabled()) {
            inputManager.update();
        }

        // Update all our gameobjects
        for(GameObject gameObject : gameObjects) {
            gameObject.update();
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
