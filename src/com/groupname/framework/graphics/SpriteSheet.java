package com.groupname.framework.graphics;

import com.groupname.framework.util.Strings;
import javafx.scene.image.Image;

import java.security.InvalidParameterException;
import java.util.Objects;

public class SpriteSheet {

    private final String name;
    private final Image image;

    public SpriteSheet(String name, Image image) {
        this.name = Strings.requireNonNullAndNotEmpty(name);
        this.image = Objects.requireNonNull(image, "Sheet cannot be null");
    }

    public int getWidth() {
        return (int)image.getWidth();
    }

    public int getHeight() {
        return (int)image.getHeight();
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }
}
