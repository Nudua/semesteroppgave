package com.groupname.framework.input.devices;

import com.groupname.framework.concurrency.TaskRunner;
import com.groupname.framework.serial.SerialPort;
import com.groupname.framework.serial.SerialPortException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;


/**
 * The HitboxInput is a custom built 'hitbox' input adapter that communicates
 * the current state of the buttons via the serial port as a single command byte
 * followed by two bytes that gives state of the joystick and buttons.
 *
 * Users must call the stop method before closing any applications to ensure that
 * the thread reading from the serial port gets cleaned up.
 */
public class HitboxInput implements InputAdapter {
    private static final String BUTTON_PREFIX = "Hitbox - ";
    private static final int GET_STATE_COMMAND = 0xFF;

    private boolean enabled;
    private final SerialPort serialPort;

    private volatile boolean isRunning;
    private final Set<String> internalState;
    private final TaskRunner pollThread;

    /**
     * Creates a new instance of this class with the specified serialPort reader.
     * A new thread will automatically be started and will read from the device until
     * the stop method is called, or if an SerialPort error occurs.
     *
     * @param serialPort the serialport used for communicating to the hitbox device.
     *                   the port should already be open and ready for reading.
     */
    public HitboxInput(SerialPort serialPort) {
        this.serialPort = Objects.requireNonNull(serialPort);

        internalState = new HashSet<>();

        pollThread = new TaskRunner(Executors.newSingleThreadExecutor());

        start();
    }

    private void start() {
        isRunning = true;
        pollThread.submit(this::run);

        enabled = true;
    }

    private void run() {
        assert serialPort.isOpen();

        while (isRunning) {
            try {
                // Read the command byte
                int command = readByte();

                if(command == GET_STATE_COMMAND) {
                    // The next two bytes will be the state of the Joystick and Buttons
                    int buttonsState = readShort();

                    parseState(buttonsState);
                }

            } catch (SerialPortException e) {
                System.err.println(e.getMessage());
                isRunning = false;
                break;
            }
        }
    }

    private void parseState(int buttonsState) {
        HitboxButton[] hitboxButtons = HitboxButton.values();

        for(HitboxButton button : hitboxButtons) {
            if((buttonsState & button.getBitmask()) == button.getBitmask()) {
                internalState.add(getButtonAsString(button));
            }
        }
    }

    // Read a single byte from the serial port
    private int readByte() throws SerialPortException {
        byte[] buffer = new byte[1];

        serialPort.read(buffer, buffer.length);

        // Make sure that we get the unsigned version
        return (buffer[0] & 0xFF);
    }

    // Read two bytes from the serial port, then shift them into a single integer.
    private int readShort() throws SerialPortException {
        byte[] buffer = new byte[2];
        serialPort.read(buffer, buffer.length);

        // Shift the two next bytes into a single int
        return (buffer[0] & 0xFF) | ((buffer[1] & 0xFF) << 8);
    }

    /**
     * Stops the currently running thread from polling input from the hitbox device via the serial port.
     *
     * @throws InterruptedException if there was an error while stopping the thread.
     */
    public void stop() throws InterruptedException {
        isRunning = false;
        pollThread.stop();
    }

    /**
     * Returns whether this InputAdapter is currently enabled or not.
     *
     * @return true if the InputAdapter is currently enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether this InputAdapter is enabled or not.
     *
     * @param enabled true to enabled, false to disable.
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Updates the given Set with inputs describing the currently pressed buttons on the hitbox.
     *
     * @param digitalInput the collection to add the currently pressed inputs.
     */
    @Override
    public void update(Set<String> digitalInput) {
        if(!enabled) {
            return;
        }

        digitalInput.addAll(internalState);

        internalState.clear();
    }

    /**
     * Helper method that gets the String representation of the specified HitboxButton
     * with the prefix used internally.
     *
     * @param button the button to get the String representation of.
     * @return the String representation of the HitboxButton with the prefix used internally.
     */
    public static String getButtonAsString(HitboxButton button) {
        return BUTTON_PREFIX + button.name();
    }
}
