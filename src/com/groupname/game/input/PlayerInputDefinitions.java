package com.groupname.game.input;

import com.groupname.framework.input.InputBinding;
import com.groupname.framework.input.devices.HitboxInput;
import com.groupname.framework.input.devices.KeyboardInput;

import java.util.HashSet;
import java.util.Set;

// This should be contained within our settings class somewhere
public class PlayerInputDefinitions {
    public static final String UP = "Player 1 - Up";
    public static final String DOWN = "Player 1 - Down";
    public static final String LEFT = "Player 1 - Left";
    public static final String RIGHT = "Player 1 - Right";

    public static final String SHOOT_UP = "Player 1 - Shoot Up";
    public static final String SHOOT_DOWN = "Player 1 - Shoot Down";
    public static final String SHOOT_LEFT = "Player 1 - Shoot Left";
    public static final String SHOOT_RIGHT = "Player 1 - Shoot Right";

    public static final String START = "Player 1 - Start";
    public static final String SELECT = "Player 1 - Select";

    public static final String SPECIAL_1 = "Player 1 - Special 1";
    public static final String SPECIAL_2 = "Player 1 - Special 2";

    // Move somewhere else?
    public static Set<InputBinding> getDefaultBindings() {
        Set<InputBinding> bindings = new HashSet<>();

        bindings.add(new InputBinding(UP, KeyboardInput.Defaults.UP, HitboxInput.Defaults.UP));
        bindings.add(new InputBinding(DOWN, KeyboardInput.Defaults.DOWN, HitboxInput.Defaults.DOWN));
        bindings.add(new InputBinding(LEFT, KeyboardInput.Defaults.LEFT, HitboxInput.Defaults.LEFT));
        bindings.add(new InputBinding(RIGHT, KeyboardInput.Defaults.RIGHT, HitboxInput.Defaults.RIGHT));

        bindings.add(new InputBinding(SHOOT_UP, KeyboardInput.Defaults.SHOOT_UP, HitboxInput.Defaults.SHOOT_UP));
        bindings.add(new InputBinding(SHOOT_DOWN, KeyboardInput.Defaults.SHOOT_DOWN, HitboxInput.Defaults.SHOOT_DOWN));
        bindings.add(new InputBinding(SHOOT_LEFT, KeyboardInput.Defaults.SHOOT_LEFT, HitboxInput.Defaults.SHOOT_LEFT, HitboxInput.Defaults.SELECT));
        bindings.add(new InputBinding(SHOOT_RIGHT, KeyboardInput.Defaults.SHOOT_RIGHT, HitboxInput.Defaults.SHOOT_RIGHT, HitboxInput.Defaults.START));

        bindings.add(new InputBinding(START, KeyboardInput.Defaults.START, HitboxInput.Defaults.START));
        bindings.add(new InputBinding(SELECT, KeyboardInput.Defaults.SELECT, HitboxInput.Defaults.SELECT));

        bindings.add(new InputBinding(SPECIAL_1, KeyboardInput.Defaults.SPECIAL_1));
        bindings.add(new InputBinding(SPECIAL_2, KeyboardInput.Defaults.SPECIAL_2));

        return bindings;
    }
}
