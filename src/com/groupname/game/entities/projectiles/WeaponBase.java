package com.groupname.game.entities.projectiles;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.framework.util.Strings;
import com.groupname.game.entities.Actor;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Objects;

public abstract class WeaponBase implements Weapon {

    private boolean enabled;
    // WeaponAttributes?

    public WeaponBase() {
        enabled = true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // more general?
    protected abstract void createProjectiles();
}

