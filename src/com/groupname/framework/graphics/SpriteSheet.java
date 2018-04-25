package com.groupname.framework.graphics;

import com.groupname.framework.util.Strings;
import javafx.scene.image.Image;

import java.security.InvalidParameterException;
import java.util.Objects;

/**
 * A Spritesheet is based on an image that holds a collection of Sprites.
 */
public class SpriteSheet {
    private final String name;
    private final Image image;

    /**
     * Creates a new instance of this class with the specified name and image.
     * The name parameter cannot be null or empty "".
     *
     * @param name the name to use for this Spritesheet.
     * @param image the image to use for this SpriteSheet.
     * @throws NullPointerException if either name or image is null.
     */
    public SpriteSheet(String name, Image image) {
        this.name = Strings.requireNonNullAndNotEmpty(name);
        this.image = Objects.requireNonNull(image, "Sheet cannot be null");
    }

    /**
     * Gets the width of the Image used by this SpriteSheet.
     * @return the width of the Image used by this SpriteSheet.
     */
    public int getWidth() {
        return (int)image.getWidth();
    }

    /**
     * Gets the height of the Image used by this SpriteSheet.
     * @return the height of the Image used by this SpriteSheet.
     */
    public int getHeight() {
        return (int)image.getHeight();
    }

    /**
     * Gets the name of this SpriteSheet.
     *
     * @return the name of this SpriteSheet.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the Image used by this SpriteSheet.
     * @return the Image used by this SpriteSheet.
     */
    public Image getImage() {
        return image;
    }

    /**
     * The string representation of a SpriteSheet where name = name and image = value
     * Where name is the name of the field you save, and value is the value of the field
     *
     * @return a String representation of this SpriteSheet instance.
     */
    @Override
    public String toString() {
        return "SpriteSheet{" +
                "name='" + name + '\'' +
                ", image=" + image +
                '}';
    }
}
