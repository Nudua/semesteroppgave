package com.groupname.framework.input.xinput;

import com.groupname.framework.input.xinput.interop.GamePadButton;
import com.groupname.framework.input.xinput.interop.XInputLibrary;
import com.groupname.framework.input.xinput.interop.structs.XInputState;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents a single XInput GamePad and it allows for polling the state of it's buttons.
 */
public class GamePad {

    private final PlayerIndex playerIndex;
    private boolean connected = true;

    private XInputState currentState;

    //60 frames per second times 60 = 60 seconds delay
    private static final int disconnectedDelay = 60;
    private int disconnectedCounter = 0;

    /**
     * Creates a new instance of this XInput GamePad with the specified PlayerIndex.
     *
     * @param playerIndex the playerIndex to use for this GamePad.
     */
    public GamePad(PlayerIndex playerIndex) {
        this.playerIndex = Objects.requireNonNull(playerIndex);
    }

    /**
     * Updates the state of this controller using the specified
     * @param library
     */
    public void poll(XInputLibrary library) {
        Objects.requireNonNull(library);

        // Don't poll as often if we're not connected
        if(!connected && disconnectedCounter <= disconnectedDelay) {
            disconnectedCounter++;
            return;
        }

        // Clear the current state
        currentState = new XInputState();

        int status = library.XInputGetState(playerIndex.getIndex(), currentState);

        // The controller is not longer connected
        if(status == XInputLibrary.ERROR_DEVICE_NOT_CONNECTED) {
            connected = false;
            disconnectedCounter = 0;
        } else {
            connected = true;
        }
    }

    /**
     * Returns the current state of all the digital buttons for this GamePad.
     *
     * @return the current state of all the digital buttons for this GamePad.
     */
    public Set<String> getState() {
        if(!connected) {
            // Just return an empty Set if we're not connected
            return new HashSet<>();
        }

        Set<String> internalState = new HashSet<>();

        GamePadButton[] digitalButtons = GamePadButton.values();

        for(GamePadButton button : digitalButtons) {
            if(isDown(button)) {
                internalState.add(XInput.getButtonAsString(playerIndex, button));
            }
        }

        return internalState;
    }

    // Check if a digital button is being pressed
    private boolean isDown(GamePadButton button) {
        assert button != null;
        return (currentState.XInputGamepad.Buttons & button.getBitmask()) == button.getBitmask();
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */

    @Override
    public String toString() {
        return "GamePad{" +
                "playerIndex=" + playerIndex +
                ", connected=" + connected +
                ", currentState=" + currentState +
                ", disconnectedCounter=" + disconnectedCounter +
                '}';
    }
}
