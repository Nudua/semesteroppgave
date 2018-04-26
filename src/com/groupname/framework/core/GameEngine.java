package com.groupname.framework.core;

import com.groupname.framework.math.Size;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class GameEngine {

    protected Canvas canvas;

    protected int width;
    protected int height;

    protected long frameCounter;

    private volatile boolean running = false;
    private volatile boolean paused = false;

    protected Color background = Color.CORNFLOWERBLUE;


    private AnimationTimer animationTimer;


    public GameEngine(int width, int height) {
        this.width = width;
        this.height = height;

        createAnimationTimer();
    }


    private void createAnimationTimer() {
        animationTimer = new GameLoopTimer(this::update, this::draw);
    }

    public Size getScreenBounds() {
        return new Size(width, height);
    }

    protected boolean isPaused() {
        return paused;
    }

    // not sure yet
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isRunning() {
        return running;
    }

    public void start() {
        if(running) {
            return;
        }

        running = true;

        animationTimer.start();
    }

    public void stop() {
        running = false;
        animationTimer.stop();
    }


    protected abstract void update();

    protected abstract void draw();
}
