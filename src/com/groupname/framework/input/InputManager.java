package com.groupname.framework.input;

import com.groupname.framework.input.devices.InputAdapter;
import com.groupname.framework.input.devices.KeyboardInput;
import javafx.scene.Scene;

import java.util.*;

public class InputManager {
    private boolean enabled = true;

    private final List<InputAdapter> inputAdapters;

    private Set<String> lastInputState;
    private final Set<String> globalInputState;

    public InputManager(Scene parent) {
        Objects.requireNonNull(parent);

        lastInputState = new HashSet<>();
        globalInputState = new HashSet<>();
        inputAdapters = new ArrayList<>();

        initializeInputAdapters(parent);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private void initializeInputAdapters(Scene parent) {
        // Keyboard
        inputAdapters.add(new KeyboardInput(parent));
    }

    public void update() {
        lastInputState.clear();
        lastInputState.addAll(globalInputState);
        // Clear the old state of the inputs
        globalInputState.clear();

        for(InputAdapter adapter : inputAdapters) {
            if(adapter.isEnabled()) {
                adapter.update(globalInputState);
            }
        }
    }

    // Holding
    public boolean isDown(String keyName) {
        return globalInputState.contains(keyName);
    }

    // Single press
    public boolean isPressed(String keyName) {
        return !lastInputState.contains(keyName) && globalInputState.contains(keyName);
    }

}
