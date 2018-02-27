package com.groupname.framework.graphics.background;

public interface ScreenTransition {
    void update();
    void draw();
    void reset();
    boolean isDone();
}
