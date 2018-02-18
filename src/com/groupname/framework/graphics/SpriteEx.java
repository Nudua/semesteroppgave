package com.groupname.framework.graphics;

import com.groupname.framework.math.IntVector2D;
import com.groupname.framework.util.Strings;
import com.groupname.framework.math.IntVector2D;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class SpriteEx {

    private final String name;
    private final String spriteSheet;

    protected Rectangle spriteRegion;

    public SpriteEx(String name, String spriteSheet, Rectangle spriteRegion) {
        this.name = Strings.requireNonNullAndNotEmpty(name);
        this.spriteSheet = Strings.requireNonNullAndNotEmpty(spriteSheet);

        this.spriteRegion = Objects.requireNonNull(spriteRegion);
    }

    public String getName() {
        return name;
    }

    public String getSpriteSheet() {
        return spriteSheet;
    }

    public Rectangle getSpriteRegion() {
        return spriteRegion;
    }

    public void setSpriteRegion(Rectangle spriteRegion) {
        this.spriteRegion = spriteRegion;
    }

    // Do something about this
    public static Rectangle createSpriteRegion(int smallX, int smallY, int width, int height) {
        return createSpriteRegion(smallX, smallY, width, height, new IntVector2D());
    }

    public static Rectangle createSpriteRegion(int smallX, int smallY, int width, int height, IntVector2D offset) {
        return new Rectangle(smallX * width + offset.getX(), smallY * height + offset.getY(), width, height);
    }
}
