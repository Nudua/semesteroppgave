package com.groupname.game.levels;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.input.InputManager;
import com.groupname.game.core.Game;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.levels.core.LevelBase;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Title extends LevelBase {

    private static final String LEVEL_ID = "0045c879-f50f-4918-b0f6-b213f7e2b522";

    public Title(Game parent, GraphicsContext graphicsContext) {
        super(parent, graphicsContext);

        backgroundColor = Color.BLACK;
    }

    @Override
    public String getId() {
        return LEVEL_ID;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void update() {
        //inputManager.update();

        if(inputManager.isDown(PlayerInputDefinitions.DOWN)) {
            graphicsContext.setFill(Color.GREEN);
            graphicsContext.setFont(Font.font(60));
            graphicsContext.fillText("PRESSING DOWN", 10, 500);
        }
    }

    @Override
    public void draw() {

        clearScreen();

        graphicsContext.setFill(Color.GREEN);
        graphicsContext.setFont(Font.font(60));
        graphicsContext.fillText("We're on the title screen!", 10, 50);

    }
}
