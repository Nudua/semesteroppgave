package com.groupname.framework.io;

/**
 * Enum list for our supported resource types.
 */
public enum ResourceType {
    /**
     * An image file with multiple sprites.
     */
    SpriteSheet,

    /**
     * An image file that represents a sprite.
     */
    Sprite,

    /**
     * An audio file that plays in the background.
     */
    Music,

    /**
     * An audio file that get triggered by specific actions.
     */
    SoundEffect,

    /**
     * An image file that represent a background.
     */
    Background,

    /**
     * An level file that contain a specific level.
     */
    Level,
    /**
     * Meta data used for creating various game objects.
     */
    Metadata
}
