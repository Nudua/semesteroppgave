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
     * @param name the name to use for this spritesheet.
     * @param image the image to use for this spritesheet.
     * @throws NullPointerException if either name or image is null.
     */
    public SpriteSheet(String name, Image image) {
        this.name = Strings.requireNonNullAndNotEmpty(name);
        this.image = Objects.requireNonNull(image, "Sheet cannot be null");
    }

    /**
     * Gets the width of the Image used by this spritesheet.
     * @return the width of the Image used by this spritesheet.
     */
    public int getWidth() {
        return (int)image.getWidth();
    }

    /**
     * Gets the height of the Image used by this spritesheet.
     * @return the height of the Image used by this spritesheet.
     */
    public int getHeight() {
        return (int)image.getHeight();
    }

    /**
     * Gets the name of this SPRITE_SHEET.
     *
     * @return the name of this SPRITE_SHEET.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the Image used by this spritesheet.
     * @return the Image used by this spritesheet.
     */
    public Image getImage() {
        return image;
    }

    /**
     * The string representation of a spritesheet where name = name and image = value
     * Where name is the name of the field you save, and value is the value of the field
     *
     * @return a String representation of this spritesheet instance.
     */
    @Override
    public String toString() {
        return "SPRITE_SHEET{" +
                "name='" + name + '\'' +
                ", image=" + image +
                '}';
    }
}
