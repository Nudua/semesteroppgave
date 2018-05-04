package com.groupname.game.core;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.input.InputManager;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

import java.util.*;
import java.util.function.Consumer;

/**
 * A concrete implementation of GameEngine which is used to drive updates and drawing
 * at a rate of 60 frames per second.
 *
 * Users must use the initialize method to say which canvas to draw to and
 * which methods that should be run every frame to update game logic and to draw the game.
 */
public class Game extends GameEngine {
    private Consumer<InputManager> onUpdate;
    private Runnable onDraw;

    private InputManager inputManager;

    private boolean initialized = false;

    /**
     * Creates a new instance of this object, input will be pulled from the specified scene.
     *
     * @param owner the scene to pull input from.
     * @param width the width used by this game.
     * @param height the height used by this game.
     */
    public Game(Scene owner, int width, int height) {
        super(width, height);
        inputManager = new InputManager(owner);
    }

    /**
     * Gets the inputmanager associated with this game.
     *
     * @return the inputmanager associated with this game.
     */
    public InputManager getInputManager() {
        return inputManager;
    }

    /**
     * Initializes this game with the canvas to draw on and the specified update and draw
     * methods to call 60 times every second.
     *
     * @param canvas the canvas to draw on.
     * @param onUpdate the method to use for updating game logic.
     * @param onDraw the method used to draw on the canvas.
     */
    public void initialize(Canvas canvas, Consumer<InputManager> onUpdate, Runnable onDraw) {
        this.canvas = Objects.requireNonNull(canvas);
        // These are optional
        this.onUpdate = onUpdate;
        this.onDraw = onDraw;

        initialized = true;
    }

    @Override
    protected void update() {
        if(!initialized) {
            return;
        }

        if(onUpdate != null) {
            onUpdate.accept(inputManager);
        }
    }

    @Override
    protected void draw() {
        if(!initialized) {
            return;
        }

        if(onDraw != null) {
            onDraw.run();
        }
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return super.toString() +
                "Game{" +
                "onUpdate=" + onUpdate +
                ", onDraw=" + onDraw +
                ", inputManager=" + inputManager +
                ", initialized=" + initialized +
                '}';
    }
}
