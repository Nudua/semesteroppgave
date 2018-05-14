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
     * A music file.
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
     * A file that contains a serialized level.
     */
    LEVEL,
    /**
     * Meta data used for creating various game objects.
     */
    METADATA
}
