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

public abstract class GameEngine {

    private Timeline gameLoop;
    private Canvas canvas;

    protected int width;
    protected int height;

    protected GraphicsContext graphicsContext;

    protected long frameCounter;

    private volatile boolean running = false;
    private volatile boolean paused = false;

    protected Color background = Color.CORNFLOWERBLUE;

    protected Scene scene;

    private AnimationTimer animationTimer;

    public GameEngine(Pane parent, int width, int height) {
        this.width = width;
        this.height = height;

        this.scene = new Scene(parent);

        canvas = new Canvas(width, height);
        graphicsContext = canvas.getGraphicsContext2D();

        // Fix
        //((GridPane)(parent)).add(canvas, 0, 1, 1, 1);
        GridPane.setRowIndex(canvas, 1);
        parent.getChildren().add(canvas);


        //buildAndSetGameLoop();
        createAnimationTimer();

    }

    private void createAnimationTimer() {
        animationTimer = new AnimationTimer() {
            private final long targetDuration = 15_000_000;
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                long duration = now - lastUpdate;

                // Around 60fps
                if(duration >= targetDuration) {
                    update();
                    draw();
                    lastUpdate = now;
                    //System.out.println(duration);
                }
            }
        };
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

        if(running) {
            return;
        }

        running = true;
        //gameLoop.play();



        animationTimer.start();

        //Thread thread = new Thread(this::run);
        //thread.start();
    }

    public void stop() {
        running = false;
        //gameLoop.stop();
        animationTimer.stop();
    }

    protected void setSize(double width, double height) {
        canvas.setWidth(width);
        canvas.setHeight(height);
    }


    private void run() {

        int frameRate = 60;

        long lastTime = System.nanoTime();
        long frameTime = 1000000000 / frameRate;

        while(running) {
            long currentTime = System.nanoTime();
            long updateTime = currentTime - lastTime;

            lastTime = currentTime;

            long sleepDuration = (frameTime - updateTime) / 1000000;

            if(sleepDuration > 0) {
                try {
                    //System.out.println(sleepDuration);
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //running = false;
                }
            }
            Platform.runLater(() -> {
                update();
                draw();
            });

        }
    }


    /*
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
    */

    protected abstract void update();

    protected abstract void draw();
}
