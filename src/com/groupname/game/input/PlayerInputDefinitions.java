package com.groupname.game.input;

import com.groupname.framework.input.InputBinding;
import com.groupname.framework.input.devices.HitboxInput;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.framework.input.xinput.PlayerIndex;
import com.groupname.framework.input.xinput.XInput;
import com.groupname.framework.input.xinput.interop.GamePadButton;
import com.groupname.framework.input.devices.HitboxButton;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

/**
 * This class defines the default player input bindings for this game.
 */
public class PlayerInputDefinitions {
    /**
     * Player 1 Movement Up.
     */
    public static final String UP = "Player 1 - UP";

    /**
     * Player 1 Movement Down.
     */
    public static final String DOWN = "Player 1 - DOWN";

    /**
     * Player 1 Movement Left.
     */
    public static final String LEFT = "Player 1 - LEFT";

    /**
     * Player 1 Movement Right.
     */
    public static final String RIGHT = "Player 1 - RIGHT";

    /**
     * Player 1 Shoots Up.
     */
    public static final String SHOOT_UP = "Player 1 - SHOOT UP";

    /**
     * Player 1 Shoots Down.
     */
    public static final String SHOOT_DOWN = "Player 1 - SHOOT DOWN";

    /**
     * Player 1 Shoots Left.
     */
    public static final String SHOOT_LEFT = "Player 1 - SHOOT LEFT";

    /**
     * Player 1 Shoots Right.
     */
    public static final String SHOOT_RIGHT = "Player 1 - SHOOT RIGHT";

    /**
     * Player 1 Start Button.
     */
    public static final String START = "Player 1 - Start";

    /**
     * Player 1 Select Button.
     */
    public static final String SELECT = "Player 1 - Select";

    /**
     * Returns the default bindings.
     *
     * @return the default bindings.
     */
    public static Set<InputBinding> getDefaultBindings() {
        Set<InputBinding> bindings = new HashSet<>();

        // Bind these Constants to all our current InputAdapters, that means: Keyboard, XInput-controller and our own Hitbox-Controller.
        bindings.add(new InputBinding(UP, KeyboardInput.getKeyCodeAsString(KeyCode.W), HitboxInput.getButtonAsString(HitboxButton.UP), XInput.getButtonAsString(PlayerIndex.ONE, GamePadButton.XINPUT_GAMEPAD_DPAD_UP)));
        bindings.add(new InputBinding(DOWN, KeyboardInput.getKeyCodeAsString(KeyCode.S), HitboxInput.getButtonAsString(HitboxButton.DOWN), XInput.getButtonAsString(PlayerIndex.ONE, GamePadButton.XINPUT_GAMEPAD_DPAD_DOWN)));
        bindings.add(new InputBinding(LEFT, KeyboardInput.getKeyCodeAsString(KeyCode.A), HitboxInput.getButtonAsString(HitboxButton.LEFT), XInput.getButtonAsString(PlayerIndex.ONE, GamePadButton.XINPUT_GAMEPAD_DPAD_LEFT)));
        bindings.add(new InputBinding(RIGHT, KeyboardInput.getKeyCodeAsString(KeyCode.D), HitboxInput.getButtonAsString(HitboxButton.RIGHT), XInput.getButtonAsString(PlayerIndex.ONE, GamePadButton.XINPUT_GAMEPAD_DPAD_RIGHT)));

        bindings.add(new InputBinding(SHOOT_UP, KeyboardInput.getKeyCodeAsString(KeyCode.UP), HitboxInput.getButtonAsString(HitboxButton.SHOOT_UP), XInput.getButtonAsString(PlayerIndex.ONE, GamePadButton.XINPUT_GAMEPAD_Y)));
        bindings.add(new InputBinding(SHOOT_DOWN, KeyboardInput.getKeyCodeAsString(KeyCode.DOWN), HitboxInput.getButtonAsString(HitboxButton.SHOOT_DOWN), XInput.getButtonAsString(PlayerIndex.ONE, GamePadButton.XINPUT_GAMEPAD_A)));
        bindings.add(new InputBinding(SHOOT_LEFT, KeyboardInput.getKeyCodeAsString(KeyCode.LEFT), HitboxInput.getButtonAsString(HitboxButton.SHOOT_LEFT), XInput.getButtonAsString(PlayerIndex.ONE, GamePadButton.XINPUT_GAMEPAD_X)));
        bindings.add(new InputBinding(SHOOT_RIGHT, KeyboardInput.getKeyCodeAsString(KeyCode.RIGHT), HitboxInput.getButtonAsString(HitboxButton.SHOOT_RIGHT), XInput.getButtonAsString(PlayerIndex.ONE, GamePadButton.XINPUT_GAMEPAD_B)));

        bindings.add(new InputBinding(START, KeyboardInput.getKeyCodeAsString(KeyCode.ENTER), HitboxInput.getButtonAsString(HitboxButton.START), XInput.getButtonAsString(PlayerIndex.ONE, GamePadButton.XINPUT_GAMEPAD_START)));
        bindings.add(new InputBinding(SELECT, KeyboardInput.getKeyCodeAsString(KeyCode.ESCAPE), HitboxInput.getButtonAsString(HitboxButton.SELECT), XInput.getButtonAsString(PlayerIndex.ONE, GamePadButton.XINPUT_GAMEPAD_BACK)));

        return bindings;
    }
}
