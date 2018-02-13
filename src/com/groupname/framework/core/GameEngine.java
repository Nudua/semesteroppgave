package com.groupname.framework.core;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
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

    protected Color background = Color.CORNFLOWERBLUE;

    public GameEngine(Pane parent, int width, int height) {

        this.width = width;
        this.height = height;

        canvas = new Canvas(width, height);
        graphicsContext = canvas.getGraphicsContext2D();

        // Fix
        ((GridPane)(parent)).add(canvas, 0, 1, 1, 1);

        buildAndSetGameLoop();
    }

    public void start() {
        gameLoop.play();
    }

    public void stop() {
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
