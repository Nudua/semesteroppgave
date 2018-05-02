package com.groupname.game.editor.metadata;

import com.groupname.framework.math.Vector2D;
import com.groupname.framework.util.Strings;

import java.io.Serializable;
import java.util.Objects;

/**
 * The main task for ObjectMetaData is to give us information about the object.
 * We can as well set the position of the object, and take copies.
 */
public class ObjectMetaData implements Serializable {
    private final String name;
    private final Vector2D position;
    private final Class<?> type;

    /**
     * Creates an new instance of ObjectMetaData.
     *
     * @param name of the object.
     * @param type class of the object.
     */
    public ObjectMetaData(String name, Class<?> type) {
        this.name = Strings.requireNonNullAndNotEmpty(name);
        this.type = Objects.requireNonNull(type);
        this.position = new Vector2D();
    }

    /**
     * Returns the position of the object.
     *
     * @return the position of the object.
     */
    public Vector2D getPosition() {
        return new Vector2D(position);
    }

    /**
     * Sets the position of the object.
     *
     * @param position of the object.
     */
    public void setPosition(Vector2D position) {
        this.position.set(position);
    }

    /**
     * Returns the name of the object.
     *
     * @return the name of the object.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the class type of the object.
     *
     * @return the class type of the object.
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * Returns a new Object with the same name, type and position.
     *
     * @return a new Object with the same name, type and position.
     */
    public ObjectMetaData deepCopy() {
        ObjectMetaData objectMetaData = new ObjectMetaData(name, type);
        objectMetaData.setPosition(position);
        return objectMetaData;
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "ObjectMetaData{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", type=" + type +
                '}';
    }
}
