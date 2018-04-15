package com.groupname.game.levels.core;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.SpriteSheetNotFoundException;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteBatchFX;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.math.Size;
import com.groupname.game.core.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

// Maybe remove the word base
public abstract class LevelBase {

    protected boolean initialized;
    // Maybe just access directly from game, or create a getter?
    //protected GraphicsContext graphicsContext;

    protected SpriteBatch spriteBatch;
    protected InputManager inputManager;
    protected GraphicsContext graphicsContext;

    protected LevelState state;

    protected Size screenBounds;

    protected Color backgroundColor;

    private final Map<String, SpriteSheet> spriteSheets;
    protected final List<GameObject> gameObjects;


    // Figure out the actual overloads, probably just pass the game and get stuff from there
    public LevelBase(Game parent, GraphicsContext graphicsContext) {
        this.screenBounds = parent.getScreenBounds();
        this.inputManager = parent.getInputManager();
        this.graphicsContext = Objects.requireNonNull(graphicsContext);

        this.spriteBatch = new SpriteBatchFX(graphicsContext);
        this.spriteSheets = new HashMap<>();
        this.gameObjects = new ArrayList<>();

        backgroundColor = Color.BLACK;

        // Set to loading first when we have screen transitions
        state = LevelState.Playing;
    }

    public abstract String getId();

    // Remove?
    protected void addSpriteSheet(SpriteSheet spriteSheet) {
        spriteSheets.put(spriteSheet.getName(), spriteSheet);
    }

    /**
     * Returns the SpriteSheet with the specified key if it exists
     * @param key the name of the SpriteSheet to get
     * @return @code {SpriteSheet} if successful.
     * @throws SpriteSheetNotFoundException if no SpriteSheet by that key was present
     */
    protected SpriteSheet getSpriteSheet(String key) {
        if(spriteSheets.containsKey(key)) {
            return spriteSheets.get(key);
        } else {
            throw new SpriteSheetNotFoundException();
        }
    }

    public void removeSpriteSheet(SpriteSheet spriteSheet) {
        spriteSheets.remove(spriteSheet.getName());
    }

    public void reset() {
        state = LevelState.Playing;
    }

    public LevelState getState() {
        return state;
    }

    protected void clearScreen() {
        clearScreen(backgroundColor);
    }

    // Maybe move into the GameEngine class, or into some drawing class
    protected void clearScreen(Color fill) {

        graphicsContext.setFill(fill);
        graphicsContext.fillRect(0, 0, screenBounds.getWidth(), screenBounds.getHeight());
    }

    public boolean isInitialized() {
        return initialized;
    }

    public abstract void initialize();
    public abstract void update();
    public abstract void draw();
}
