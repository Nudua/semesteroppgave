package com.groupname.game.controllers;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.editor.metadata.ObjectMetaData;

import java.util.Objects;

/**
 * This class is used for storing and restoring LevelItems,
 * as well as placing and manipulating these items in within the leveleditor.
 */
public class LevelItem {
    private ObjectMetaData metaData;
    private GameObject instance;

    private boolean player;
    private boolean placed;

    /**
     * Creates a new instance of this class with the specified metadata and gameobject instance.
     * @param metaData the metadata to associate with this item..
     * @param instance the gameObject to associate with this item.
     * @throws NullPointerException if metaData or instance is null.
     */
    public LevelItem(ObjectMetaData metaData, GameObject instance) {
        this.metaData = Objects.requireNonNull(metaData);
        this.instance = Objects.requireNonNull(instance);
    }

    /**
     * Sets the position of the metaData and the gameObject associated with this LevelItem.
     * @param position the position to set.
     */
    public void setPosition(Vector2D position) {
        metaData.setPosition(position);
        if(instance != null) {
            instance.setPosition(position);
        }
    }

    /**
     * Gets the position associated with this instance.
     * Specially it gets the position of the metaData.
     * @return
     */
    public Vector2D getPosition() {
        return metaData.getPosition();
    }

    /**
     * Returns true if the GameObject associated with this object is of the Player class.
     * @return true if the GameObject associated with this object is of the Player class.
     */
    public boolean isPlayer() {
        return player;
    }

    /**
     * Sets whether this LevelItem holds a Player object.
     * @param player
     */
    public void setPlayer(boolean player) {
        this.player = player;
    }

    /**
     * Returns whether this levelItem is placed.
     * @return true if this item is placed, false otherwise.
     */
    public boolean isPlaced() {
        return placed;
    }

    /**
     * Sets whether this item is placed or not.
     * @param placed the value to set.
     */
    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    /**
     * Gets the metaData associated with this LevelItem.
     * @return the metaData associated with this LevelItem.
     */
    public ObjectMetaData getMetaData() {
        return metaData;
    }

    /**
     * Gets the GameObject instance associated with this LevelItem.
     * @return the GameObject instance associated with this LevelItem.
     */
    public GameObject getInstance() {
        return instance;
    }
}
