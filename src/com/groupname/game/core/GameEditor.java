package com.groupname.game.core;

import com.groupname.framework.core.Difficulty;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.history.UndoRedo;
import com.groupname.framework.history.commands.ListAddCommand;
import com.groupname.framework.input.devices.MouseInput;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.level.Tile;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.controllers.LevelItem;
import com.groupname.game.editor.metadata.EnemyMetaData;
import com.groupname.game.editor.metadata.LevelFactory;
import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.levels.core.LevelBase;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    //private List<GameObject> gameObjects = new ArrayList<>();
    private List<LevelItem> levelItems;
    private LevelItem selectedItem;

    private UndoRedo commandHistory;

    public GameEditor(Game game, Canvas canvas, List<LevelItem> levelItems, UndoRedo commandHistory) {
        super(game, canvas.getGraphicsContext2D());

        this.levelItems = Objects.requireNonNull(levelItems);
        this.commandHistory = Objects.requireNonNull(commandHistory);

        mouseInput = new MouseInput(canvas, Tile.Size);
        backgroundColor = Color.BLACK;

        levelFactory = new LevelFactory(inputManager);
        levelFactory.initialize();

        // Can return type be T?
        //player = (Player)levelFactory.create(new ObjectMetaData("Player", Player.class, new Vector2D(100,500)));

        //EnemyMetaData guardEnemy = new EnemyMetaData("Guard - Easy", GuardEnemy.class, new Vector2D(500,500));
        //guardEnemy.setDifficulty(Difficulty.Easy);

        //gameObjects.add(levelFactory.create(guardEnemy));

        mouseInput.setOnMove((x, y) -> {
            if(selectedItem != null && !selectedItem.isPlaced()) {
                selectedItem.setPosition(new Vector2D(x * Tile.Size, y * Tile.Size));
            }
        });

        mouseInput.setOnClicked((x, y) -> {
            if(selectedItem != null) {
                selectedItem.setPosition(new Vector2D(x * Tile.Size, y * Tile.Size));
                selectedItem.setPlaced(true);

                commandHistory.execute(new ListAddCommand<>(levelItems, selectedItem));

                selectedItem = null;
            }
        });
    }

    public void setSelectedItem(LevelItem selectedItem) {
        this.selectedItem = selectedItem;
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


        if(mode == Mode.Playing) {
            for(LevelItem item : levelItems) {
                item.getInstance().update();
            }
        }
    }

    @Override
    public void draw() {
        drawBackground();

        if(selectedItem != null) {
            selectedItem.getInstance().draw(spriteBatch);
        }

        for(LevelItem item : levelItems) {
            item.getInstance().draw(spriteBatch);
        }
    }

    private void drawBackground() {
        graphicsContext.drawImage(backgroundImage, 0, 0);
    }
}
