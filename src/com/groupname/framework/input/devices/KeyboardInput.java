package com.groupname.framework.input.devices;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.*;

/**
 * This class grabs input from the specified Scene and consolidates keyboard events it into a Set,
 * so that it can be used to query keyboard events in a polling fashion.
 */
public class KeyboardInput implements InputAdapter {

    private static final String KEYCODE_PREFIX = "Keyboard - ";

    private boolean enabled = true;
    // Contains the internal keyboard state of this instance.
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
     * Returns whether this InputAdapter is enabled or not.
     *
     * @return whether this InputAdapter is enabled or not.
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Enable or disable updating this instance.
     *
     * @param enabled true to enabled, false to disable.
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private void OnKeyPressed(KeyEvent event) {
        internalState.add(getKeyCodeAsString(event.getCode()));
    }

    private void OnKeyReleased(KeyEvent event) {
        internalState.remove(getKeyCodeAsString(event.getCode()));
    }

    /**
     * Updates the specified set with the internal currently pressed keys.
     *
     * @param digitalInput the collection to add the currently pressed keys.
     */
    @Override
    public void update(Set<String> digitalInput) {
        Objects.requireNonNull(digitalInput);
        // Update the global input state with the keyboard presses
        digitalInput.addAll(internalState);
    }

    /**
     * Returns all the fields as a string representation of the object.
     * Enabled tells if this input adapter is enabled.
     * internalState holds all the keys currently pressed on this keyboard adapter.
     *
     * @return all the fields as a string representation of the object.
     */
    @Override
    public String toString() {
        return "KeyboardInput{" +
                "enabled=" + enabled +
                ", internalState=" + internalState +
                '}';
    }

    /**
     * Helper method that gets the String representation of the specified KeyCode
     * with the prefix used internally.
     *
     * @param keyCode the key to get the String representation of.
     * @return the String representation of the KeyCode with the prefix used internally.
     */
    public static String getKeyCodeAsString(KeyCode keyCode) {
        return KEYCODE_PREFIX + keyCode.getName();
    }
}
