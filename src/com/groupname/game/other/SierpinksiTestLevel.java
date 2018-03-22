package com.groupname.game.other;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.animation.LinearAnimation;
import com.groupname.framework.graphics.background.ArrowScreenTransition;
import com.groupname.framework.graphics.background.ScreenTransition;
import com.groupname.framework.graphics.background.SierpinskiTriangleBackground;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.levels.core.LevelState;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

public class SierpinksiTestLevel extends LevelBase {

    private AnimatedSprite animatedSprite;
    private SierpinskiTriangleBackground sierpinskiBackground;
    private ScreenTransition arrowScreenTransition;

    public SierpinksiTestLevel(GameEngine game, InputManager inputManager) {
        super(game, inputManager);
        backgroundColor = Color.DARKSLATEBLUE;
    }

    public void initialize() {
        createSpriteSheets();

        createAnim();

        sierpinskiBackground = new SierpinskiTriangleBackground(graphicsContext);
        arrowScreenTransition = new ArrowScreenTransition(graphicsContext);
    }

    private void createSpriteSheets() {

        String spriteSheetFolder = "../resources/graphics/spritesheets/";

        Image sheet1 = new Image(getClass().getResourceAsStream(spriteSheetFolder + "spritesheet1.png"));

        addSpriteSheet(new SpriteSheet("sheet1", sheet1));
    }

    private void createAnim() {

        Rectangle frame1 = Sprite.createSpriteRegion(0,0,64,64);
        Rectangle frame2 = Sprite.createSpriteRegion(1,0,64,64);
        Rectangle frame3 = Sprite.createSpriteRegion(0,1,64,64);
        Rectangle frame4 = Sprite.createSpriteRegion(1,1,64,64);

        animatedSprite = new AnimatedSprite(getSpriteSheet("sheet1"), Arrays.asList(frame1, frame2, frame3, frame4));
        animatedSprite.setAnimationLogic(new LinearAnimation(30));
    }

    public void update() {
        if(inputManager.isEnabled()) {
            inputManager.update();
        }

        if(inputManager.wasPressed(PlayerInputDefinitions.SELECT)) {
            state = LevelState.Completed;
            arrowScreenTransition.reset();

        }

        if(arrowScreenTransition.isDone()) {
            animatedSprite.stepAnimation();

            sierpinskiBackground.update();
        } else {
            arrowScreenTransition.update();
        }


    }

    public void draw() {


        //spriteBatch.draw(animatedSprite, new Vector2D(400, 400));

        if(arrowScreenTransition.isDone()) {
            clearScreen();
            sierpinskiBackground.draw();
        } else {
            arrowScreenTransition.draw();
        }


    }
}
