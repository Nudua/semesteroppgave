package com.groupname.game.editor.metadata;

import com.groupname.framework.level.TilePattern;
import com.groupname.framework.level.TileType;
import com.groupname.framework.math.Vector2D;

import java.util.Objects;

public class TileMetaData extends ObjectMetaData {
    private TileType tileType = TileType.Block;
    private TilePattern tilePattern = TilePattern.Ground;

    public TileMetaData(String name, Class<?> type, Vector2D position) {
        super(name, type, position);
    }

    public void setTilePattern(TilePattern tilePattern) {
        this.tilePattern = Objects.requireNonNull(tilePattern);
    }

    public TilePattern getTilePattern() {
        return tilePattern;
    }

    public void setTileType(TileType tileType) {
        this.tileType = Objects.requireNonNull(tileType);
    }

    public TileType getTileType() {
        return tileType;
    }
}
