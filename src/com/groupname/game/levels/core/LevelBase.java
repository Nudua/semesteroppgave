package com.groupname.game.levels.core;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteBatchFX;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.math.Size;
import com.groupname.game.core.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * This class serves as a base for every levels contained within the game
 * and has common components used for every level such as a SpriteBatch, InputManager
 * and a GraphicsContext.
 */
public abstract class LevelBase {
    protected boolean initialized;

    protected final SpriteBatch spriteBatch;
    protected final InputManager inputManager;
    protected final GraphicsContext graphicsContext;

    protected LevelState state;

    protected Size screenBounds;

    protected Color backgroundColor;

    protected final List<GameObject> gameObjects;

    /**
     * Subclasses must call this constructor to setup the level with the required common components.
     *
     * @param parent the game that is the parent of this level.
     * @param graphicsContext the graphicsContext used for drawing this level.
     */
    public LevelBase(Game parent, GraphicsContext graphicsContext) {
        Objects.requireNonNull(parent);
        this.screenBounds = parent.getScreenBounds();
        this.inputManager = parent.getInputManager();
        this.graphicsContext = Objects.requireNonNull(graphicsContext);

        this.spriteBatch = new SpriteBatchFX(graphicsContext);
        this.gameObjects = new ArrayList<>();

        backgroundColor = Color.BLACK;

        state = LevelState.PLAYING;
    }

    /**
     * Implementations must implement this method and return a unique identifier for this level.
     *
     * Tip: Strong unique id's may be generated using UUID.randomUUID().toString();
     *
     * @return the unique identifier for this level.
     */
    public abstract String getId();

    /**
     * Implementations must reset the state of the level using this method.
     */
    public void reset() {
        state = LevelState.PLAYING;
    }

    /**
     * Returns the current state of the level.
     *
     * @return the current state of the level.
     */
    public LevelState getState() {
        return state;
    }

    protected void clearScreen() {
        clearScreen(backgroundColor);
    }

    // Clears the screen with the specified color fill
    protected void clearScreen(Color fill) {
        graphicsContext.setFill(fill);
        graphicsContext.fillRect(0, 0, screenBounds.getWidth(), screenBounds.getHeight());
    }

    /**
     * Returns whether this level has been successfully initialized.
     *
     * @return whether this level has been successfully initialized.
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Implementations must use this method to initialize the current level.
     *
     * They must also set initialize to true if it has been successfully initialized.
     */
    public abstract void initialize();

    /**
     * Implementations must use this method to update any game logic.
     * This method will be called 60 times per second.
     */
    public abstract void update();

    /**
     * Implementations must use this method to draw the level.
     * This method will be called 60 times per second.
     */
    public abstract void draw();

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "LevelBase{" +
                "initialized=" + initialized +
                ", spriteBatch=" + spriteBatch +
                ", inputManager=" + inputManager +
                ", graphicsContext=" + graphicsContext +
                ", state=" + state +
                ", screenBounds=" + screenBounds +
                ", backgroundColor=" + backgroundColor +
                ", gameObjects=" + gameObjects +
                '}';
    }
}
