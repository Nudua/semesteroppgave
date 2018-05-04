package com.groupname.game.other;

import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.background.transitions.ArrowScreenTransition;
import com.groupname.framework.graphics.background.transitions.ScreenTransition;
import com.groupname.framework.graphics.background.SierpinskiTriangleBackground;
import com.groupname.game.core.Game;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.levels.core.LevelState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SierpinksiTestLevel extends LevelBase {

    @Override
    public String getId() {
        return null;
    }

    private SierpinskiTriangleBackground sierpinskiBackground;
    private ScreenTransition arrowScreenTransition;
    private SpriteSheet spriteSheet;

    public SierpinksiTestLevel(Game game, GraphicsContext graphicsContext) {
        super(game, graphicsContext);
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

        spriteSheet = new SpriteSheet("sheet1", sheet1);
    }

    @Override
    public void update() {
        if(inputManager.isEnabled()) {
            inputManager.update();
        }

        if(inputManager.wasPressed(PlayerInputDefinitions.SELECT)) {
            state = LevelState.COMPLETED;
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
