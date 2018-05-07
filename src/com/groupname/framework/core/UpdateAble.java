package com.groupname.framework.core;

/**
 * Represents components that are able to update themselves.
 */
public interface UpdateAble {
    /**
     * Implementations should use this method to update all logic every frame.
     */
    void update();
}
