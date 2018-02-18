package com.groupname.framework.graphics.animation;

import com.groupname.framework.graphics.SpriteEx;

public interface AnimationLogic<T> {
    void step(T sprite);
    void reset(T sprite);
}
