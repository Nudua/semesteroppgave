package com.groupname.framework.graphics.animation;

public interface AnimationLogic<T> {
    void step(T sprite);
    void reset(T sprite);
}
