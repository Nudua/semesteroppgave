package com.groupname.framework.input.devices;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.*;

public class KeyboardInput implements InputAdapter {

    public static class Defaults {
        private static final String prefix = "Keyboard - ";

        // Enum maybe? maybe not
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

    public KeyboardInput(Scene scene) {
        Objects.requireNonNull(scene);
        internalState = new HashSet<>();

        scene.setOnKeyPressed(this::OnKeyPressed);
        scene.setOnKeyReleased(this::OnKeyReleased);
    }

    public boolean isEnabled() {
        return enabled;
    }

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

    public void update(Set<String> digitalInput) {
        Objects.requireNonNull(digitalInput);
        // Update the global input state with the keyboard presses
        digitalInput.addAll(internalState);
    }
}
