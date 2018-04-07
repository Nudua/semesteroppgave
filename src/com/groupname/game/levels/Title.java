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

    public Title(Game parent, GraphicsContext graphicsContext) {
        super(parent, graphicsContext);

        backgroundColor = Color.BLACK;
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
