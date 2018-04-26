package com.groupname.framework.input.devices;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.*;

/**
 * Input from keyboard.
 */
public class KeyboardInput implements InputAdapter {
    /**
     * Default keyboard binding.
     * WADS control the waliking.
     * Arrows control the shooting.
     */
    public static class Defaults {
        private static final String prefix = "Keyboard - ";

        public static final String UP = prefix + KeyCode.W.getName();
        public static final String DOWN = prefix + KeyCode.S.getName();
        public static final String LEFT = prefix + KeyCode.A.getName();
        public static final String RIGHT = prefix + KeyCode.D.getName();

        public static final String SHOOT_UP = prefix + KeyCode.UP.getName();
        public static final String SHOOT_DOWN = prefix + KeyCode.DOWN.getName();
        public static final String SHOOT_LEFT = prefix + KeyCode.LEFT.getName();
        public static final String SHOOT_RIGHT = prefix + KeyCode.RIGHT.getName();

        public static final String START = prefix + KeyCode.ENTER.getName();
        public static final String SELECT = prefix + KeyCode.ESCAPE.getName();

        public static final String SPECIAL_1 = prefix + KeyCode.SPACE.getName();
        public static final String SPECIAL_2 = prefix + KeyCode.KP_LEFT.getName();

    }

    private boolean enabled = true;
    // Contains the internal keyboard state of this class
    private final Set<String> internalState;

    /**
     * Gets inputs from the specified scene.
     *
     * @param scene the scene you are at.
     */
    public KeyboardInput(Scene scene) {
        Objects.requireNonNull(scene);
        internalState = new HashSet<>();

        scene.setOnKeyPressed(this::OnKeyPressed);
        scene.setOnKeyReleased(this::OnKeyReleased);
    }

    /**
     * Return if KeyboardInput are enable or disable.
     *
     * @return if KeyboardInput are enable or disable.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Enable or disable KeyboardInput.
     *
     * @param enabled true or false.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private void OnKeyPressed(KeyEvent event) {
        //String key = Defaults.prefix + event.getCode().getName();

        internalState.add(Defaults.prefix + event.getCode().getName());

        // Just for debugging purposes
        //System.out.println(key);
    }

    private void OnKeyReleased(KeyEvent event) {
        internalState.remove(Defaults.prefix + event.getCode().getName());
    }

    /**
     * Updates the specified set with the internal currently pressed keys.
     *
     * @param digitalInput the collection to add the currently pressed keys.
     */
    public void update(Set<String> digitalInput) {
        Objects.requireNonNull(digitalInput);
        // Update the global input state with the keyboard presses
        digitalInput.addAll(internalState);
    }
}
