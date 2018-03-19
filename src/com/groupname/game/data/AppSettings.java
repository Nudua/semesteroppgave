package com.groupname.game.data;

import com.groupname.framework.math.Size;


public enum AppSettings {
    INSTANCE;

    public static final Size SCREEN_BOUNDS = new Size(1200, 720);
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