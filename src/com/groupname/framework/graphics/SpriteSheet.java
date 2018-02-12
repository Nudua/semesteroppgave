package com.groupname.framework.graphics;

import javafx.scene.image.Image;

import java.security.InvalidParameterException;
import java.util.Objects;

public class SpriteSheet {

    private final String name;
    private final int width;
    private final int height;
    private final Image image;

    public SpriteSheet(String name, int width, int height, Image image) {
        if(name == null || "".equals(name)) {
            throw new NullPointerException("name cannot be empty or null");
        }

        this.name = name;

        if(width <= 0 || height <= 0) {
            throw new InvalidParameterException("width and height cannot be less than or equal to 0");
        }

        this.width = width;
        this.height = height;

        this.image = Objects.requireNonNull(image, "Sheet cannot be null");
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }
}
