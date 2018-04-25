package com.groupname.game.core;

import com.groupname.framework.core.Difficulty;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.background.transitions.ScreenTransition;
import com.groupname.framework.history.UndoRedo;
import com.groupname.framework.history.commands.ListAddCommand;
import com.groupname.framework.history.commands.ListRemoveCommand;
import com.groupname.framework.input.devices.MouseInput;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.level.Tile;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.controllers.LevelItem;
import com.groupname.game.editor.metadata.EnemyMetaData;
import com.groupname.game.editor.metadata.LevelFactory;
import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.entities.Actor;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.levels.core.LevelBase;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


// Convert to level/View

public class GameEditor extends LevelBase {

    private final static String LEVEL_ID = "cd4305d4-d624-4c4e-9ef8-77207cf9b4e1";

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

    private Rectangle levelBounds = new Rectangle(160, 80, 1280 - 160 * 2, 720 - 80 * 2);

    public GameEditor(Game game, Canvas canvas, List<LevelItem> levelItems, UndoRedo commandHistory) {
        super(game, canvas.getGraphicsContext2D());

        this.levelItems = Objects.requireNonNull(levelItems);
        this.commandHistory = Objects.requireNonNull(commandHistory);

        mouseInput = new MouseInput(canvas, levelBounds);
        backgroundColor = Color.BLACK;

        levelFactory = new LevelFactory(inputManager);

        // MoveCommand? simply this
        mouseInput.setOnMove(this::updateItemPosition);

        mouseInput.setOnClicked((x, y) -> {
            boolean wasMoved = updateItemPosition(x, y);

            if(wasMoved) {
                selectedItem.setPlaced(true);

                if(!levelItems.contains(selectedItem)) {
                    commandHistory.execute(new ListAddCommand<>(levelItems, selectedItem));
                } // else update position command

                selectedItem = null;
            } else {

                Point2D clickPoint = new Point2D(x,y);
                System.out.println("Here");

                for(LevelItem levelItem : levelItems) {
                    //Vector2D itemPosition = levelItem.getInstance().getPosition();
                    Vector2D itemPosition = levelItem.getPosition();

                    //System.out.println("Instance:" + itemPosition.toString() + "Object: " + shouldBeSame.toString());
                    // Horrible
                    Rectangle hitbox = new Rectangle(itemPosition.getX(), itemPosition.getY(), levelItem.getInstance().getSprite().getWidth(), levelItem.getInstance().getSprite().getHeight());

                    if(hitbox.contains(clickPoint)) {
                        levelItem.setPlaced(false);
                        selectedItem = levelItem;
                        break;
                    }
                }

            }
        });
    }

    @Override
    public String getId() {
        return LEVEL_ID;
    }

    public void setMode(Mode mode) {
        this.mode = Objects.requireNonNull(mode);
    }

    private boolean updateItemPosition(double x, double y) {
        if(selectedItem != null && !selectedItem.isPlaced()) {
            Sprite sprite = selectedItem.getInstance().getSprite();
            Vector2D newPosition = getRelativePosition(x, y, sprite);
            if(isWithinBounds(selectedItem.getInstance(), newPosition)) {
                selectedItem.setPosition(newPosition);
                return true;
            }
        }
        return false;
    }

    private boolean isWithinBounds(GameObject gameObject, Vector2D position) {
        //Vector2D position = gameObject.getPosition();
        Size size = new Size((int)gameObject.getSprite().getWidth(), (int)gameObject.getSprite().getHeight());

        // Right side
        if(position.getX() + size.getWidth() >= levelBounds.getX() + levelBounds.getWidth()) {
            return false;
        }

        // Left side
        if(position.getX() <= levelBounds.getX()) {
            return false;
        }

        // Top
        if(position.getY() <= levelBounds.getY()) {
            return false;
        }

        // Bottom
        if(position.getY() + size.getHeight() >= levelBounds.getY() + levelBounds.getHeight()) {
            return false;
        }

        return true;
    }

    private Vector2D getRelativePosition(double x, double y, Sprite sprite) {
        assert sprite != null;
        return new Vector2D(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
    }

    public void setSelectedItem(LevelItem selectedItem) {
        this.selectedItem = null;
        this.selectedItem = selectedItem;
    }

    @Override
    public void initialize() {
        backgroundImage = Content.loadImage("background4.png", ResourceType.Background);

    }

    private List<GameObject> getGameObjects() {
        return levelItems.stream().map(LevelItem::getInstance).collect(Collectors.toList());
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

                if(item.getInstance() instanceof Player) {
                    ((Player) item.getInstance()).checkCollision(getGameObjects());
                }

                item.getInstance().update();
            }
        } else {

            // Delete the currently selected item
            if(inputManager.wasPressed(PlayerInputDefinitions.SELECT)) {
                deleteSelectedItem();
            }

            for(LevelItem item : levelItems) {
                Sprite sprite = item.getInstance().getSprite();

                AnimatedSprite.stepIfAnimatedSprite(sprite);
            }
        }
    }

    public void deleteSelectedItem() {
        if(selectedItem != null && selectedItem.getMetaData().getType() != Player.class && !selectedItem.isPlaced()) {
            if(levelItems.contains(selectedItem)) {
                commandHistory.execute(new ListRemoveCommand<>(levelItems, selectedItem));
            }
            selectedItem = null;
        }
    }

    @Override
    public void reset() {
        mode = Mode.Editing;
        for(LevelItem item : levelItems) {
            item.setPosition(item.getPosition());

            if(item.getInstance() instanceof Actor) {
                ((Actor) item.getInstance()).setAlive(true);
            }
        }
    }

    @Override
    public void draw() {
        drawBackground();

        if(selectedItem != null && !levelItems.contains(selectedItem)) {
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
