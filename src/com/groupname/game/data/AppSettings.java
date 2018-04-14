package com.groupname.game.data;

import com.groupname.framework.math.Size;
import javafx.scene.shape.Rectangle;


public enum AppSettings {
    INSTANCE;

    public static final Size SCREEN_BOUNDS = new Size(1280, 720);
    public static final Rectangle LEVEL_BOUNDS = new Rectangle(80 * 2, 80 * 1, 1280 - 80 * 4, 720 - 80 * 2);
    private boolean fullScreen;
    private String nickname;

    public void save() {

    }

    public void load() {

    }

}
//AppSettings.INSTANCE.save();
//AppSettings.INSTANCE.load();
//AppSettings.SCREEN_BOUNDS;