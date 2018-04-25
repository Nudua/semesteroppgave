package com.groupname.framework.input;

import com.groupname.framework.input.devices.HitboxInput;
import com.groupname.framework.input.devices.InputAdapter;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.framework.input.xinput.LibraryNotFoundException;
import com.groupname.framework.input.xinput.XInput;
import com.groupname.framework.serial.SerialPort;
import com.groupname.framework.serial.SerialPortException;
import com.groupname.framework.serial.SerialPortFactory;
import com.groupname.game.input.PlayerInputDefinitions;
import javafx.scene.Scene;

import java.util.*;

public class InputManager {
    private boolean enabled = true;

    private final List<InputAdapter> inputAdapters;

    private final Set<String> internalInputState;

    private Set<InputBinding> bindings;

    private Set<String> lastState = new HashSet<>();
    private Set<String> state = new HashSet<>();

    public InputManager(Scene parent) {
        Objects.requireNonNull(parent);

        internalInputState = new HashSet<>();
        inputAdapters = new ArrayList<>();

        // Set these from another place
        bindings = PlayerInputDefinitions.getDefaultBindings();//new HashSet<>(); //

        initializeInputAdapters(parent);
    }

    public void updateScene(Scene scene) {
        for(int i = 0; i < inputAdapters.size(); i++) {
            if(inputAdapters.get(i) instanceof KeyboardInput) {
                inputAdapters.remove(inputAdapters.get(i));
                inputAdapters.add(new KeyboardInput(scene));
            }
        }
    }

    public void setBindings(Set<InputBinding> bindings) {
        this.bindings.clear();
        this.bindings.addAll(bindings);
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

        initializeXInput();
        initializeHitbox();
    }

    private void initializeXInput() {
        try {
            XInput xInput = new XInput();
            xInput.initialize();
            inputAdapters.add(xInput);
        } catch (LibraryNotFoundException ex) {
            System.out.println("XInput library not found");
        }
    }

    private void initializeHitbox() {
        SerialPort serialPort = null;

        try {
            serialPort = SerialPortFactory.create();

            serialPort.open();

        } catch (SerialPortException e) {
            serialPort = null;
            System.out.println(e.getMessage());
        }

        if(serialPort == null) {
            // Unable to initialize the serialport class
            System.err.println("Unable to load the serialPort class");
            return;
        }

        inputAdapters.add(new HitboxInput(serialPort));
    }

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

    public boolean isDown(String buttonName) {
        return state.contains(buttonName);
    }

    // Single press
    public boolean wasPressed(String buttonName) {
        return !lastState.contains(buttonName) && state.contains(buttonName);
    }

    // Holding
    private boolean internalButtonIsDown(String keyName) {
        return internalInputState.contains(keyName);
    }

    public void stop() {
        System.out.println("Stopping inputadapter...");
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
    /*
    // Single press
    public boolean isPressed(String keyName) {
        return !lastInputState.contains(keyName) && globalInputState.contains(keyName);
    }
    */

}
