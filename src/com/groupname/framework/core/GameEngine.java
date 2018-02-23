package com.groupname.framework.core;

import com.groupname.framework.math.Size;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public abstract class GameEngine {

    private Timeline gameLoop;
    private Canvas canvas;

    protected int width;
    protected int height;

    protected GraphicsContext graphicsContext;

    protected long frameCounter;

    private volatile boolean running = true;
    private volatile boolean paused = false;

    protected Color background = Color.CORNFLOWERBLUE;

    protected Scene scene;

    public GameEngine(Pane parent, int width, int height) {
        this.width = width;
        this.height = height;

        this.scene = new Scene(parent);

        canvas = new Canvas(width, height);
        graphicsContext = canvas.getGraphicsContext2D();

        // Fix
        ((GridPane)(parent)).add(canvas, 0, 1, 1, 1);

        buildAndSetGameLoop();
    }

    public Size getScreenBounds() {
        return new Size(width, height);
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public Scene getScene() {
        return scene;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isRunning() {
        return running;
    }

    public void start() {
        running = true;
        gameLoop.play();
    }

    public void stop() {
        running = false;
        gameLoop.stop();
    }

    protected void setSize(double width, double height) {
        canvas.setWidth(width);
        canvas.setHeight(height);
    }

    private void buildAndSetGameLoop() {
        int framesPerSecond = 60;

        Duration oneFrameAmt = Duration.millis(1000 / framesPerSecond);
        KeyFrame oneFrame = new KeyFrame(oneFrameAmt, event -> {

            if(!running) {
                return;
            }

            frameCounter++;
            update();
            draw();
        });

        // sets the framework world's framework loop (Timeline)
        gameLoop = TimelineBuilder.create()
                .cycleCount(Animation.INDEFINITE)
                .keyFrames(oneFrame)
                .build();
    }

    protected abstract void update();

    protected abstract void draw();
}
