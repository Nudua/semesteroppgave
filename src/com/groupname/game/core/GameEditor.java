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
import com.groupname.game.levels.core.LevelBase;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


// Convert to level/View

public class GameEditor extends LevelBase {

    public enum Mode {
        Editing,
        Playing
    }

    private final MouseInput mouseInput;
    private Image backgroundImage;

    private Mode mode = Mode.Editing;

    public GameEditor(Game game, Canvas canvas) {
        super(game, canvas.getGraphicsContext2D());

        mouseInput = new MouseInput(canvas);
        backgroundColor = Color.BLACK;
    }

    @Override
    public void initialize() {
        backgroundImage = Content.loadImage("background1.png", ResourceType.Background);
    }

    @Override
    public void update() {
        // Clear the screen
        clearScreen();

        Vector2D movingPosition = mouseInput.getMovingCoordinates();
        Vector2D pressedPosition = mouseInput.getPressedCoordinates();

        graphicsContext.fillText(String.format("Moving X: %f, Y: %f", movingPosition.getX(), movingPosition.getY()), 10, 20);
        graphicsContext.fillText(String.format("Pressed X: %f, Y: %f", pressedPosition.getX(), pressedPosition.getY()), 10, 40);
    }

    @Override
    public void draw() {

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
