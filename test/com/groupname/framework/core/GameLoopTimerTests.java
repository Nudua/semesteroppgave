package com.groupname.framework.core;

import javafx.animation.AnimationTimer;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameLoopTimerTests {

    private final int updateTestValue = 42;
    private final int drawTestValue = 42;

    private int counter = 0;
    private final Runnable validUpdate = () -> {counter = updateTestValue; };
    private final Runnable validDraw = () -> { counter = drawTestValue; };

    @Test(expected = NullPointerException.class)
    public void updateParameterCannotBeNull() {
        new GameLoopTimer(null, validDraw);
    }

    @Test(expected = NullPointerException.class)
    public void drawParameterCannotBeNull() {
        new GameLoopTimer(validUpdate, null);
    }
}
