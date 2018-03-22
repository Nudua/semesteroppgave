package com.groupname.framework.graphics.background.transitions;

public interface ScreenTransition {
    void update();
    void draw();
    void reset();
    boolean isDone();
}
