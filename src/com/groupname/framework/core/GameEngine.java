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

    //private Timeline gameLoop;
    protected Canvas canvas;

    protected int width;
    protected int height;

    //private GraphicsContext graphicsContext;

    protected long frameCounter;

    private volatile boolean running = false;
    private volatile boolean paused = false;

    protected Color background = Color.CORNFLOWERBLUE;

    //protected scene scene;

    private AnimationTimer animationTimer;

    //private Consumer<Boolean> togglePauseMenu;

    public GameEngine(int width, int height) {
        this.width = width;
        this.height = height;

        //this.scene = new scene(parent);

        //canvas = new Canvas(width, height);
        //graphicsContext = canvas.getGraphicsContext2D();
        //canvas.setOpacity(0.5);

        // Fix
        //((GridPane)(parent)).add(canvas, 0, 1, 1, 1);
        //GridPane.setRowIndex(canvas, 1);
        //parent.getChildren().add(canvas);
        //parent.getChildren().add(0, canvas);

        createAnimationTimer();
    }

    /*
    public void setTogglePauseMenu(Consumer<Boolean> togglePauseMenu) {
       this.togglePauseMenu = togglePauseMenu;
    }
    */

    /*
    public void setCanvas(Canvas canvas) {
        this.canvas = Objects.requireNonNull(canvas);
    }
    */

    /*
    public GraphicsContext getGraphicsContext() {
        return canvas.getGraphicsContext2D();
    }
    */

    private void createAnimationTimer() {
        animationTimer = new GameLoopTimer(this::update, this::draw);
    }

    public Size getScreenBounds() {
        return new Size(width, height);
    }

    /*
    protected Canvas getCanvas() {
        return canvas;
    }
    */

    /*
    public scene getScene() {
        return scene;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        if(togglePauseMenu != null) {
            togglePauseMenu.accept(paused);
        }

        this.paused = paused;

        double opacity = paused ? 0.5 : 1.0;
        canvas.setOpacity(opacity);
    }
    */

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

    /*
    protected void setSize(double width, double height) {
        canvas.setWidth(width);
        canvas.setHeight(height);
    }
    */


    /*
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
    */

    protected abstract void update();

    protected abstract void draw();
}
