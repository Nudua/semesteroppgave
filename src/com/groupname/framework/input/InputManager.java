package com.groupname.framework.input;

import com.groupname.framework.input.devices.HitboxInput;
import com.groupname.framework.input.devices.InputAdapter;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.framework.util.LibraryNotFoundException;
import com.groupname.framework.input.xinput.XInput;
import com.groupname.framework.serial.SerialPort;
import com.groupname.framework.serial.SerialPortException;
import com.groupname.framework.serial.SerialPortFactory;
import com.groupname.game.input.PlayerInputDefinitions;
import javafx.scene.Scene;

import java.util.*;

/**
 * Gathers and updates input from different input adapters. (Keyboard, gamepads, custom controllers)
 * that implements the InputAdapter interface.
 */
public class InputManager {
    private boolean enabled = true;

    private final List<InputAdapter> inputAdapters;
    private final Set<String> internalInputState;

    private Set<InputBinding> bindings;

    private Set<String> lastState = new HashSet<>();
    private Set<String> state = new HashSet<>();

    /**
     * Creates a new instance an takes the inputs from the scene
     * and use it with the keyboard adapter.
     *
     * @param parent the current scene you are at.
     */
    public InputManager(Scene parent) {
        Objects.requireNonNull(parent);

        internalInputState = new HashSet<>();
        inputAdapters = new ArrayList<>();

        bindings = PlayerInputDefinitions.getDefaultBindings();

        initializeInputAdapters(parent);
    }

    /**
     * Updates the keyboardInput to use this scene.
     *
     * @param scene the specified scene you change to.
     */
    public void updateScene(Scene scene) {
        Objects.requireNonNull(scene);
        for(int i = 0; i < inputAdapters.size(); i++) {
            if(inputAdapters.get(i) instanceof KeyboardInput) {
                inputAdapters.remove(inputAdapters.get(i));
                inputAdapters.add(new KeyboardInput(scene));
            }
        }
    }


    /**
     * Sets the bindins associated wit this InputManager.
     *
     * @param bindings the new Set of Bindings to use.
     */
    public void setBindings(Set<InputBinding> bindings) {
        this.bindings.clear();
        this.bindings.addAll(bindings);
    }

    /**
     * Return true if the current inputManager is enabled and false if it is disabled.
     *
     * @return true if the current inputManager is enabled and false if it is disabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Enabled or disabled this InputManager from updating it's InputAdapters.
     *
     * @param enabled true to enable, false to disable.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private void initializeInputAdapters(Scene parent) {
        assert parent != null;

        inputAdapters.add(new KeyboardInput(parent));

        initializeXInput();
        initializeHitbox();
    }

    private void initializeXInput() {
        try {
            XInput xInput = new XInput();
            xInput.initialize();
            inputAdapters.add(xInput);
        } catch (LibraryNotFoundException ex) {
            System.err.println("XInput library not found");
        }
    }

    private void initializeHitbox() {
        SerialPort serialPort = null;

        try {
            serialPort = SerialPortFactory.create();
            serialPort.open();
        } catch (SerialPortException e) {
            serialPort = null;
            System.err.println(e.getMessage());
        }

        if(serialPort == null) {
            // Unable to initialize the serialport class
            System.err.println("Unable to load the serialPort class");
            return;
        }

        inputAdapters.add(new HitboxInput(serialPort));
    }

    /**
     * Consolidates input from every inputAdapter.
     */
    public void update() {
        // Clear the old state of the inputs
        lastState.clear();
        lastState.addAll(state);
        state.clear();

        // The actual internal button names
        internalInputState.clear();

        // Update all our inputAdapaters, keyboard, gamepad etc.
        for(InputAdapter adapter : inputAdapters) {
            if(adapter.isEnabled()) {
                adapter.update(internalInputState);
            }
        }

        // Check if any of the bound buttons are pressed
        for(InputBinding inputBinding : bindings) {
            for(String buttonName : inputBinding.getBindings()) {
                if(internalButtonIsDown(buttonName)) {
                    state.add(inputBinding.getName());
                    break;
                }
            }
        }
    }

    /**
     * Return true when the buttonName is pressed down and false if not.
     *
     * @param buttonName the name of the button to check if is down.
     * @return true when the buttonName is pressed down and false if not.
     */
    public boolean isDown(String buttonName) {
        return state.contains(buttonName);
    }

    /**
     * Method to check single press.
     * Returns true if buttonName was not pressed last frame but is pressed on the current frame
     * otherwise it will return false.
     *
     * @param buttonName the name of the button to check.
     * @return true if buttonName was not pressed last frame but is pressed now.
     */
    public boolean wasPressed(String buttonName) {
        return !lastState.contains(buttonName) && state.contains(buttonName);
    }

    // Holding
    private boolean internalButtonIsDown(String keyName) {
        return internalInputState.contains(keyName);
    }

    /**
     * If a hitboxInput is running then this method will stop the thread that it's running on.
     */
    public void stop() {
        System.out.println("Stopping inputadapters...");
        for(InputAdapter adapter : inputAdapters) {
            if(adapter instanceof HitboxInput) {
                try {
                    ((HitboxInput) adapter).stop();
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "InputManager{" +
                "enabled=" + enabled +
                ", inputAdapters=" + inputAdapters +
                ", internalInputState=" + internalInputState +
                ", bindings=" + bindings +
                ", lastState=" + lastState +
                ", state=" + state +
                '}';
    }
}
