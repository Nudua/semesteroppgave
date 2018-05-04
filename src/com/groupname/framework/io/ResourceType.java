package com.groupname.framework.io;

/**
 * Enum list for our supported resource types.
 */
public enum ResourceType {
    /**
     * An image file with multiple sprites.
     */
    SPRITE_SHEET,

    /**
     * An image file that represents a sprite.
     */
    SPRITE,

    /**
     * An audio file that plays in the background.
     */
    MUSIC,

    /**
     * An audio file that get triggered by specific actions.
     */
    SOUND_EFFECT,

    /**
     * An image file that represent a background.
     */
    BACKGROUND,

    /**
     * An level file that contain a specific level.
     */
    LEVEL,
    /**
     * Meta data used for creating various game objects.
     */
    METADATA
}
