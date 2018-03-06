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

import java.util.Arrays;

public class Level5 extends LevelBase {

    private Player player1;

    private GameObject box;
    private AnimatedSprite animatedSprite;

    public Level5(GameEngine game, InputManager inputManager) {
        super(game, inputManager);

        backgroundColor = Color.CORNFLOWERBLUE;
    }

    @Override
    public void reset() {
        super.reset();
        // Additional cleanup
        gameObjects.remove(box);
        createBox();
    }

    public void initialize() {
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

        animatedSprite = new AnimatedSprite("anim1", getSpriteSheet("sheet1"), Arrays.asList(frame1, frame2, frame3, frame4, frame5, frame6));
        animatedSprite.setAnimationLogic(new LinearAnimation(30));
    }

    private void createSpriteSheets() {

        String spriteSheetFolder = "../resources/graphics/spritesheets/";

        Image sheet1 = new Image(getClass().getResourceAsStream(spriteSheetFolder + "spritesheet1.png"));
        Image playerSheet = new Image(getClass().getResourceAsStream(spriteSheetFolder + "player1.png"));

        addSpriteSheet(new SpriteSheet("sheet1", sheet1));
        addSpriteSheet(new SpriteSheet("player1", playerSheet));
    }

    private void createBox() {
        box = new SimpleGameObject(
                new Sprite("box1", getSpriteSheet("sheet1"), Sprite.createSpriteRegion(64,64)),
                new Vector2D(200,200),
                screenBounds,
                4,
                true);

        gameObjects.add(box);
    }

    private void createPlayer1() {
        Sprite p1Sprite = new Sprite("player1Sprite", getSpriteSheet("player1"), Sprite.createSpriteRegion(160, 160));
        p1Sprite.setScale(0.5d);

        player1 = new Player(p1Sprite, new Vector2D(), 0, inputManager);

        gameObjects.add(player1);
    }

    public void update() {
        // Update input to the most recent state
        if(inputManager.isEnabled()) {
            inputManager.update();
        }

        // This should be handled by the objects itself, just to demonstrate simple collision detection
        if(box.collides(player1.getHitbox())) {
            player1.getSprite().setSpriteRegion(Sprite.createSpriteRegion(2,0,160,160));
            System.out.println("CRASH");
            state = LevelState.Completed;
        } else {
            player1.getSprite().setSpriteRegion(Sprite.createSpriteRegion(0,0,160,160));
        }

        // Update all our gameobjects
        for(GameObject gameObject : gameObjects) {
            gameObject.update();
        }

        animatedSprite.stepAnimation();
    }

    public void draw() {
        clearScreen();

        // We should consider creating a class for drawing text
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText("Use the arrow keys to move the player object!", 100, 100);

        // Draw all our gameobjects, really need to sort based on sprite priority
        for(GameObject gameObject : gameObjects) {
            gameObject.draw(spriteBatch);
        }

        spriteBatch.draw(animatedSprite, new Vector2D(200, 200));
    }
}
