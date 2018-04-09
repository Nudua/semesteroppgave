package com.groupname.game.core;

import com.groupname.framework.core.Difficulty;
import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteBatchFX;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.MouseInput;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.level.Tile;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.data.AppSettings;
import com.groupname.game.editor.EnemyMetaData;
import com.groupname.game.editor.LevelFactory;
import com.groupname.game.editor.ObjectMetaData;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.levels.core.LevelBase;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


// Convert to level/View

public class GameEditor extends LevelBase {

    public enum Mode {
        Editing,
        Playing
    }

    private final MouseInput mouseInput;
    private Image backgroundImage;

    private Mode mode = Mode.Editing;
    private final LevelFactory levelFactory;
    private Player player;

    private List<GameObject> gameObjects = new ArrayList<>();
    private boolean isSet = false;

    public GameEditor(Game game, Canvas canvas) {
        super(game, canvas.getGraphicsContext2D());

        mouseInput = new MouseInput(canvas, Tile.Size);
        backgroundColor = Color.BLACK;

        levelFactory = new LevelFactory(inputManager);
        levelFactory.initialize();

        // Can return type be T?
        player = (Player)levelFactory.create(new ObjectMetaData("Player", Player.class, new Vector2D(100,500)));

        EnemyMetaData guardEnemy = new EnemyMetaData("Guard - Easy", GuardEnemy.class, new Vector2D(500,500));
        guardEnemy.setDifficulty(Difficulty.Easy);

        //gameObjects.add(levelFactory.create(guardEnemy));

        mouseInput.setOnMove((x, y) -> {
            if(!isSet) {
                player.setPosition(new Vector2D(x * Tile.Size, y * Tile.Size));
            }
        });

        mouseInput.setOnClicked((x, y) -> {
            player.setPosition(new Vector2D(x * Tile.Size, y * Tile.Size));
            isSet = true;
        });
    }

    @Override
    public void initialize() {
        backgroundImage = Content.loadImage("background1.png", ResourceType.Background);

    }

    @Override
    public void update() {
        //inputManager.update();
        // Clear the screen
        /*
        clearScreen();

        Vector2D movingPosition = mouseInput.getMovingCoordinates();
        Vector2D pressedPosition = mouseInput.getPressedCoordinates();

        graphicsContext.fillText(String.format("Moving X: %f, Y: %f", movingPosition.getX(), movingPosition.getY()), 10, 20);
        graphicsContext.fillText(String.format("Pressed X: %f, Y: %f", pressedPosition.getX(), pressedPosition.getY()), 10, 40);
        */

        player.update();
        player.checkCollision(gameObjects);

        for(GameObject gameObject : gameObjects) {
            gameObject.update();
        }
    }

    @Override
    public void draw() {

        drawBackground();

        for(GameObject gameObject : gameObjects) {
            gameObject.draw(spriteBatch);
        }

        player.draw(spriteBatch);
    }

    private void drawBackground() {
        graphicsContext.drawImage(backgroundImage, 0, 0);
    }
}
