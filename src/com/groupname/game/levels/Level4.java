package com.groupname.game.levels;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.levels.core.LevelBase;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Level4 extends LevelBase {

    private final List<GameObject> gameObjects;

    private Sprite bulletSprite;

    public Level4(GameEngine game, InputManager inputManager) {
        super(game, inputManager);
        gameObjects = new ArrayList<>();

        backgroundColor = Color.CORNFLOWERBLUE;
    }

    public void initialize() {
        createSpriteSheets();
        createBulletSprite();
    }

    private void createSpriteSheets() {

        String spriteSheetFolder = "../resources/graphics/spritesheets/";

        Image projectilesImage = new Image(getClass().getResourceAsStream(spriteSheetFolder + "projectiles-cords.png"));

        spriteBatch.addSpritesheet("projectiles", projectilesImage);
    }

    private void createBulletSprite() {
        // The Rectangle within the image projectiles.png to get the Sprite itself
        // Sprites from this SpriteSheet is 66x66 in size, width x height
        Rectangle spriteSourceRectangle = Sprite.createSpriteRegion(0, 0, 66, 66);

        // Name = Bullet1, SpriteSheet = projectiles, SpriteRegion = spriteSourceRectangle
        bulletSprite = new Sprite("bullet1", "projectiles", spriteSourceRectangle);
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

        // Draw all our gameobjects, really need to sort based on sprite priority
        for(GameObject gameObject : gameObjects) {
            gameObject.draw(spriteBatch);
        }

        // Draw our example sprite, these are usually constructed inside gameobjects
        spriteBatch.draw(bulletSprite, new Vector2D(0,0));
    }
}
