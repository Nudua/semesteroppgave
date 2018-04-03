package com.groupname.game.core;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteBatchFX;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.MouseInput;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.data.AppSettings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;




public class GameEditor extends GameEngine {

    public enum Mode {
        Editing,
        Playing
    }

    private final InputManager inputManager;
    private final MouseInput mouseInput;
    private final SpriteBatch spriteBatch;
    private Image backgroundImage;

    private Mode mode = Mode.Editing;

    public GameEditor(Pane parent) {
        super(parent, AppSettings.SCREEN_BOUNDS.getWidth(), AppSettings.SCREEN_BOUNDS.getHeight());
        inputManager = new InputManager(scene);
        mouseInput = new MouseInput(getCanvas());
        spriteBatch = new SpriteBatchFX(graphicsContext);
        background = Color.BLACK;

        //inputManager.setEnabled(false);
        backgroundImage = Content.loadImage("background1.png", ResourceType.Background);
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

        drawBackground();

        final int tileSize = 80;

        int smallX = (int)mouseInput.getMovingCoordinates().getX() / tileSize;
        int smallY = (int)mouseInput.getMovingCoordinates().getY() / tileSize;

        int rectX = smallX * tileSize;
        int rectY = smallY * tileSize;

        boolean draw = false;

        if(smallX > 1 && smallX < 14 && smallY > 0 && smallY < 8) {
            draw = true;
        }



        if(draw) {
            graphicsContext.setFill(Color.DARKBLUE);
            graphicsContext.fillRect(rectX, rectY, tileSize, tileSize);
        }

    }

    private void drawBackground() {
        graphicsContext.drawImage(backgroundImage, 0, 0);
    }
}
