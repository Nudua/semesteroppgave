package com.groupname.game.core;

import com.groupname.framework.collision.BoundsChecker;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.history.UndoRedo;
import com.groupname.framework.history.commands.ListAddCommand;
import com.groupname.framework.history.commands.ListRemoveCommand;
import com.groupname.framework.input.devices.ContainedMouseInput;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.data.AppSettings;
import com.groupname.game.editor.LevelItem;
import com.groupname.game.entities.Actor;
import com.groupname.game.entities.Player;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.levels.core.LevelBase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;




/**
 * This class is used in conjunction with the EditorController to create new levels for the game.
 * Specially this class is the level board section of the editor and is used to manipulate level items.
 */
public class GameEditor extends LevelBase {

    private final static String LEVEL_ID = "cd4305d4-d624-4c4e-9ef8-77207cf9b4e1";

    private final ContainedMouseInput mouseInput;
    private Image backgroundImage;

    private Mode mode = Mode.EDITING;

    private final List<LevelItem> levelItems;
    private LevelItem selectedItem;

    private final UndoRedo commandHistory;

    private final Rectangle levelBounds = AppSettings.LEVEL_BOUNDS;
    private final BoundsChecker boundsChecker = new BoundsChecker();

    // Properties
    private BooleanProperty playDisabled;
    private BooleanProperty editDisabled;

    /**
     * Creates a new instance of this GameEditor.
     *
     * @param game the game that is the owner of this level.
     * @param canvas the canvas to grab input from.
     * @param levelItems a list that contains all the levelItems used by this gameEditor.
     * @param commandHistory the UnDoRedo used to redo and undo commands.
     */
    public GameEditor(Game game, Canvas canvas, List<LevelItem> levelItems, UndoRedo commandHistory) {
        super(game, canvas.getGraphicsContext2D());

        this.levelItems = Objects.requireNonNull(levelItems);
        this.commandHistory = Objects.requireNonNull(commandHistory);

        mouseInput = new ContainedMouseInput(canvas, levelBounds);
        backgroundColor = Color.BLACK;

        mouseInput.setOnMove(this::updateItemPosition);
        mouseInput.setOnClicked(this::onMouseClicked);
    }

    /**
     * Returns a property that describes whether the editor is in the PLAY mode.
     *
     * @return a property that describes whether the editor is in the PLAY mode.
     */
    public BooleanProperty playDisabledProperty() {
        if(playDisabled == null) {
            playDisabled = new SimpleBooleanProperty(false);
        }

        return playDisabled;
    }

    /**
     * Returns a property that describes whether the editor is in the EDIT mode.
     *
     * @return a property that describes whether the editor is in the EDIT mode.
     */
    public BooleanProperty editDisabledProperty() {
        if(editDisabled == null) {
            editDisabled = new SimpleBooleanProperty(true);
        }

        return editDisabled;
    }

    private void onMouseClicked(double x, double y) {
        boolean wasMoved = updateItemPosition(x, y);

        if(wasMoved) {
            selectedItem.setPlaced(true);

            if(!levelItems.contains(selectedItem)) {
                commandHistory.execute(new ListAddCommand<>(levelItems, selectedItem));
            }

            selectedItem = null;
        } else {
            Point2D clickPoint = new Point2D(x,y);

            for(LevelItem levelItem : levelItems) {
                Vector2D itemPosition = levelItem.getPosition();

                Rectangle hitbox = getHitbox(itemPosition, levelItem);

                if(hitbox.contains(clickPoint)) {
                    levelItem.setPlaced(false);
                    selectedItem = levelItem;
                    break;
                }
            }

        }
    }

    private Rectangle getHitbox(Vector2D itemPosition, LevelItem levelItem) {
        assert itemPosition != null;
        assert levelItem != null && levelItem.getInstance() != null && levelItem.getInstance().getSprite() != null;

        return new Rectangle(itemPosition.getX(), itemPosition.getY(), levelItem.getInstance().getSprite().getWidth(), levelItem.getInstance().getSprite().getHeight());
    }

    /**
     * Returns the unique id for this level.
     *
     * @return the unique id for this level.
     */
    @Override
    public String getId() {
        return LEVEL_ID;
    }

    /**
     * Sets the mode that the GameEditor is in.
     *
     * @param mode the mode to change to.
     */
    public void setMode(Mode mode) {
        this.mode = Objects.requireNonNull(mode);

        playDisabled.set(mode != Mode.EDITING);
        editDisabled.set(mode != Mode.PLAYING);
    }

    private boolean updateItemPosition(double x, double y) {
        if(selectedItem != null && !selectedItem.isPlaced()) {
            Sprite sprite = selectedItem.getInstance().getSprite();
            Vector2D newPosition = getRelativePosition(x, y, sprite);

            if(boundsChecker.isWithinBounds(selectedItem.getInstance(), levelBounds, newPosition)) {
                selectedItem.setPosition(newPosition);
                return true;
            }
        }
        return false;
    }

    private Vector2D getRelativePosition(double x, double y, Sprite sprite) {
        assert sprite != null;
        return new Vector2D(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
    }

    /**
     * Sets the currently level item that is selected by the editor.
     *
     * @param selectedItem the level item that is selected.
     */
    public void setSelectedItem(LevelItem selectedItem) {
        this.selectedItem = null;
        this.selectedItem = selectedItem;
    }

    /**
     * Loads all the resources required by the GameEditor and initializes it.
     *
     * Must be called before any call to update or draw.
     */
    @Override
    public void initialize() {
        backgroundImage = Content.loadImage("background4.png", ResourceType.BACKGROUND);

    }

    private List<GameObject> getGameObjects() {
        return levelItems.stream().map(LevelItem::getInstance).collect(Collectors.toList());
    }

    /**
     * Updates the logic used to drive this editor.
     */
    @Override
    public void update() {
        if(mode == Mode.PLAYING) {
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

    /**
     * Attempts to delete the selected item if a item is selected.
     */
    public void deleteSelectedItem() {
        if(selectedItem != null && selectedItem.getMetaData().getType() != Player.class && !selectedItem.isPlaced()) {
            if(levelItems.contains(selectedItem)) {
                commandHistory.execute(new ListRemoveCommand<>(levelItems, selectedItem));
            }
            selectedItem = null;
        }
    }

    /**
     * Resets the state of the editor and all of its level items and places it into editing mode.
     */
    @Override
    public void reset() {
        setMode(Mode.EDITING);
        for(LevelItem item : levelItems) {
            item.setPosition(item.getPosition());

            if(item.getInstance() instanceof Actor) {
                ((Actor) item.getInstance()).setAlive(true);
            }
        }
    }

    /**
     * Draws game screen of this editor.
     */
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

    /**
     * The state which the editor is in.
     */
    public enum Mode {
        /**
         * The mode when we can place new level items on the level.
         */
        EDITING,
        /**
         * This mode is used when testing the level.
         */
        PLAYING
    }
}
