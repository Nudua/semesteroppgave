package com.groupname.game.core;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteBatchFX;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.MouseInput;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.data.AppSettings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


enum GameEditorMode {
    Editing,
    Playing
}

public class GameEditor extends GameEngine {

    private final InputManager inputManager;
    private final MouseInput mouseInput;
    private final SpriteBatch spriteBatch;

    private GameEditorMode mode = GameEditorMode.Editing;

    public GameEditor(Pane parent) {
        super(parent, AppSettings.SCREEN_BOUNDS.getWidth(), AppSettings.SCREEN_BOUNDS.getHeight());
        inputManager = new InputManager(scene);
        mouseInput = new MouseInput(getCanvas());
        spriteBatch = new SpriteBatchFX(graphicsContext);
        background = Color.CORNFLOWERBLUE;

        //inputManager.setEnabled(false);
    }

    @Override
    protected void update() {
        // Clear the screen
        graphicsContext.setFill(background);
        graphicsContext.fillRect(0,0, width, height);

        graphicsContext.setFill(Color.BLACK);

        Vector2D movingPosition = mouseInput.getMovingCoordinates();
        Vector2D pressedPosition = mouseInput.getPressedCoordinates();

        graphicsContext.fillText(String.format("Moving X: %f, Y: %f", movingPosition.getX(), movingPosition.getY()), 10, 20);
        graphicsContext.fillText(String.format("Pressed X: %f, Y: %f", pressedPosition.getX(), pressedPosition.getY()), 10, 40);
    }

    @Override
    protected void draw() {

        final int tileSize = 64;

        int smallX = (int)mouseInput.getMovingCoordinates().getX() / tileSize;
        int smallY = (int)mouseInput.getMovingCoordinates().getY() / tileSize;

        int rectX = smallX * tileSize;
        int rectY = smallY * tileSize;

        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(rectX, rectY, tileSize, tileSize);
    }
}
