package com.groupname.game.levels;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.animation.LinearAnimation;
import com.groupname.framework.graphics.background.SierpinskiTriangleBackground;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
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

public class Level2 extends LevelBase {

    private AnimatedSprite animatedSprite;
    private SierpinskiTriangleBackground sierpinskiBackground;

    public Level2(GameEngine game, InputManager inputManager) {
        super(game, inputManager);
        backgroundColor = Color.DARKSLATEBLUE;
    }

    public void initialize() {
        createSpriteSheets();

        createAnim();

        sierpinskiBackground = new SierpinskiTriangleBackground(graphicsContext);
    }

    private void createSpriteSheets() {

        String spriteSheetFolder = "../resources/graphics/spritesheets/";

        Image sheet1 = new Image(getClass().getResourceAsStream(spriteSheetFolder + "spritesheet1.png"));

        spriteBatch.addSpritesheet("sheet1", sheet1);
    }

    private void createAnim() {

        Rectangle frame1 = Sprite.createSpriteRegion(0,0,64,64);
        Rectangle frame2 = Sprite.createSpriteRegion(1,0,64,64);
        Rectangle frame3 = Sprite.createSpriteRegion(0,1,64,64);
        Rectangle frame4 = Sprite.createSpriteRegion(1,1,64,64);

        animatedSprite = new AnimatedSprite("anim1", "sheet1", Arrays.asList(frame1, frame2, frame3, frame4));
        animatedSprite.setAnimationLogic(new LinearAnimation(30));
    }

    public void update() {
        if(inputManager.isEnabled()) {
            inputManager.update();
        }

        if(inputManager.isPressed(KeyboardInput.Defaults.ESCAPE)) {
            state = LevelState.Completed;
        }

        animatedSprite.stepAnimation();

        sierpinskiBackground.update();
    }

    public void draw() {
        clearScreen();

        //spriteBatch.draw(animatedSprite, new Vector2D(400, 400));

        sierpinskiBackground.draw();
    }
}
