package com.groupname.game.levels;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.animation.LinearAnimation;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SimpleGameObject;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.levels.core.LevelState;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level3 extends LevelBase {

    private Sprite bulletSprite;

    // Spritesheets
    private final String projectilesSheetName = "projectiles";

    public Level3(GameEngine game, InputManager inputManager) {
        super(game, inputManager);

        backgroundColor = Color.CORNFLOWERBLUE;
    }

    public void initialize() {
        createSpriteSheets();
        createBulletSprite();
    }

    private void createSpriteSheets() {

        String spriteSheetFolder = "../resources/graphics/spritesheets/";

        Image projectilesImage = new Image(getClass().getResourceAsStream(spriteSheetFolder + "projectiles-cords.png"));

        addSpriteSheet(new SpriteSheet(projectilesSheetName, projectilesImage));
    }

    private void createBulletSprite() {
        // The Rectangle within the image projectiles.png to get the Sprite itself
        Rectangle spriteSourceRectangle = Sprite.createSpriteRegion(0, 0, 66, 66);

        // Name = Bullet1, SpriteSheet = projectiles, SpriteRegion = spriteSourceRectangle
        bulletSprite = new Sprite("bullet1", getSpriteSheet(projectilesSheetName), spriteSourceRectangle);
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
