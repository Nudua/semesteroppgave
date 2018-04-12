package com.groupname.game.editor.metadata;

import com.groupname.framework.math.Vector2D;
import com.groupname.framework.util.Strings;

import java.util.Objects;

public class ObjectMetaData {
    private final String name;
    private final Vector2D position;
    private final Class<?> type;

    public ObjectMetaData(String name, Class<?> type, Vector2D position) {
        this.name = Strings.requireNonNullAndNotEmpty(name);
        this.type = Objects.requireNonNull(type);
        this.position = Objects.requireNonNull(position);
    }

    public Vector2D getPosition() {
        return new Vector2D(position);
    }

    public void setPosition(Vector2D position) {
        this.position.set(position);
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }
}
