package com.groupname.framework.core;

import javafx.animation.AnimationTimer;

import java.util.Objects;

// Can add a framerate counter here
public class GameLoopTimer extends AnimationTimer {
    private final Runnable update;
    private final Runnable draw;

    public GameLoopTimer(Runnable update, Runnable draw) {
        this.update = Objects.requireNonNull(update);
        this.draw = Objects.requireNonNull(draw);
    }

    @Override
    public void handle(long now) {
        update.run();
        draw.run();
    }
}
