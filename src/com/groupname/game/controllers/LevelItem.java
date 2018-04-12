package com.groupname.game.controllers;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.editor.metadata.ObjectMetaData;

import java.util.Objects;

public class LevelItem {
    private ObjectMetaData metaData;
    private GameObject instance;

    private boolean player;
    private boolean placed;

    public LevelItem(ObjectMetaData metaData, GameObject instance) {
        this.metaData = Objects.requireNonNull(metaData);
        this.instance = Objects.requireNonNull(instance);
    }

    public void setPosition(Vector2D position) {
        metaData.setPosition(position);
        if(instance != null) {
            instance.setPosition(position);
        }
    }

    public Vector2D getPosition() {
        return metaData.getPosition();
    }

    public boolean isPlayer() {
        return player;
    }

    public void setPlayer(boolean player) {
        this.player = player;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public ObjectMetaData getMetaData() {
        return metaData;
    }

    public GameObject getInstance() {
        return instance;
    }
}
