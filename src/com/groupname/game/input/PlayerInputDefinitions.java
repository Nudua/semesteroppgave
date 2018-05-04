package com.groupname.game.input;

import com.groupname.framework.input.InputBinding;
import com.groupname.framework.input.devices.HitboxInput;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.framework.input.xinput.PlayerIndex;
import com.groupname.framework.input.xinput.XInput;
import com.groupname.framework.input.xinput.interop.GamePadButton;

import java.util.HashSet;
import java.util.Set;

/**
 * This class defines the default player input bindings for this game.
 */
public class PlayerInputDefinitions {
    public static final String UP = "Player 1 - UP";
    public static final String DOWN = "Player 1 - DOWN";
    public static final String LEFT = "Player 1 - LEFT";
    public static final String RIGHT = "Player 1 - RIGHT";

    public static final String SHOOT_UP = "Player 1 - SHOOT UP";
    public static final String SHOOT_DOWN = "Player 1 - SHOOT DOWN";
    public static final String SHOOT_LEFT = "Player 1 - SHOOT LEFT";
    public static final String SHOOT_RIGHT = "Player 1 - SHOOT RIGHT";

    public static final String START = "Player 1 - Start";
    public static final String SELECT = "Player 1 - Select";

    public static final String SPECIAL_1 = "Player 1 - Special 1";
    public static final String SPECIAL_2 = "Player 1 - Special 2";

    /**
     * Returns the default bindings for this class.
     *
     * @return the default bindings for this class.
     */
    public static Set<InputBinding> getDefaultBindings() {
        Set<InputBinding> bindings = new HashSet<>();

        bindings.add(new InputBinding(UP, KeyboardInput.Defaults.UP, HitboxInput.Defaults.UP, XInput.Defaults.get(PlayerIndex.One, GamePadButton.XINPUT_GAMEPAD_DPAD_UP)));
        bindings.add(new InputBinding(DOWN, KeyboardInput.Defaults.DOWN, HitboxInput.Defaults.DOWN, XInput.Defaults.get(PlayerIndex.One, GamePadButton.XINPUT_GAMEPAD_DPAD_DOWN)));
        bindings.add(new InputBinding(LEFT, KeyboardInput.Defaults.LEFT, HitboxInput.Defaults.LEFT, XInput.Defaults.get(PlayerIndex.One, GamePadButton.XINPUT_GAMEPAD_DPAD_LEFT)));
        bindings.add(new InputBinding(RIGHT, KeyboardInput.Defaults.RIGHT, HitboxInput.Defaults.RIGHT, XInput.Defaults.get(PlayerIndex.One, GamePadButton.XINPUT_GAMEPAD_DPAD_RIGHT)));

        bindings.add(new InputBinding(SHOOT_UP, KeyboardInput.Defaults.SHOOT_UP, HitboxInput.Defaults.SHOOT_UP, XInput.Defaults.get(PlayerIndex.One, GamePadButton.XINPUT_GAMEPAD_Y)));
        bindings.add(new InputBinding(SHOOT_DOWN, KeyboardInput.Defaults.SHOOT_DOWN, HitboxInput.Defaults.SHOOT_DOWN, XInput.Defaults.get(PlayerIndex.One, GamePadButton.XINPUT_GAMEPAD_A)));
        bindings.add(new InputBinding(SHOOT_LEFT, KeyboardInput.Defaults.SHOOT_LEFT, HitboxInput.Defaults.SHOOT_LEFT, HitboxInput.Defaults.SELECT, XInput.Defaults.get(PlayerIndex.One, GamePadButton.XINPUT_GAMEPAD_X)));
        bindings.add(new InputBinding(SHOOT_RIGHT, KeyboardInput.Defaults.SHOOT_RIGHT, HitboxInput.Defaults.SHOOT_RIGHT, HitboxInput.Defaults.START, XInput.Defaults.get(PlayerIndex.One, GamePadButton.XINPUT_GAMEPAD_B)));

        bindings.add(new InputBinding(START, KeyboardInput.Defaults.START, HitboxInput.Defaults.START, XInput.Defaults.get(PlayerIndex.One, GamePadButton.XINPUT_GAMEPAD_START)));
        bindings.add(new InputBinding(SELECT, KeyboardInput.Defaults.SELECT, HitboxInput.Defaults.SELECT, XInput.Defaults.get(PlayerIndex.One, GamePadButton.XINPUT_GAMEPAD_BACK)));

        bindings.add(new InputBinding(SPECIAL_1, KeyboardInput.Defaults.SPECIAL_1));
        bindings.add(new InputBinding(SPECIAL_2, KeyboardInput.Defaults.SPECIAL_2));

        return bindings;
    }
}
