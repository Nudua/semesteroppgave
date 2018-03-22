package com.groupname.game.other;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.background.transitions.ArrowScreenTransition;
import com.groupname.framework.graphics.background.transitions.ScreenTransition;
import com.groupname.framework.graphics.background.SierpinskiTriangleBackground;
import com.groupname.framework.input.InputManager;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.levels.core.LevelState;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SierpinksiTestLevel extends LevelBase {

    private SierpinskiTriangleBackground sierpinskiBackground;
    private ScreenTransition arrowScreenTransition;

    public SierpinksiTestLevel(GameEngine game, InputManager inputManager) {
        super(game, inputManager);
        backgroundColor = Color.DARKSLATEBLUE;
    }

    @Override
    public void initialize() {
        createSpriteSheets();

        sierpinskiBackground = new SierpinskiTriangleBackground(graphicsContext);
        arrowScreenTransition = new ArrowScreenTransition(graphicsContext);
    }

    private void createSpriteSheets() {

        String spriteSheetFolder = "../resources/graphics/spritesheets/";

        Image sheet1 = new Image(getClass().getResourceAsStream(spriteSheetFolder + "spritesheet1.png"));

        addSpriteSheet(new SpriteSheet("sheet1", sheet1));
    }

    @Override
    public void update() {
        if(inputManager.isEnabled()) {
            inputManager.update();
        }

        if(inputManager.wasPressed(PlayerInputDefinitions.SELECT)) {
            state = LevelState.Completed;
            arrowScreenTransition.reset();
        }

        if(arrowScreenTransition.isDone()) {
            sierpinskiBackground.update();
        } else {
            arrowScreenTransition.update();
        }
    }

    @Override
    public void draw() {
        if(arrowScreenTransition.isDone()) {
            clearScreen();
            sierpinskiBackground.draw();
        } else {
            arrowScreenTransition.draw();
        }
    }
}
