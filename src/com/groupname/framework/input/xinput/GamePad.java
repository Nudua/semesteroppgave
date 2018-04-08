package com.groupname.framework.input.xinput;

import com.groupname.framework.input.xinput.interop.GamePadButton;
import com.groupname.framework.input.xinput.interop.XInputLibrary;
import com.groupname.framework.input.xinput.interop.structs.XInputState;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GamePad {

    private final PlayerIndex playerIndex;
    private boolean connected = true;

    private XInputState currentState;

    //60 frames per second times 60 = 60 seconds delay
    private static final int disconnectedDelay = 60;
    private int disconnectedCounter = 0;

    public GamePad(PlayerIndex playerIndex) {
        this.playerIndex = Objects.requireNonNull(playerIndex);
    }

    public void poll(XInputLibrary library) {
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
            //System.out.format("GamePad #%d was disconnected\n", playerIndex.getIndex() + 1);
        } else {
            connected = true;
            //checkJoySticks();
            //System.out.format("GamePad #%d was connected\n", playerIndex.getIndex() + 1);
        }
    }

    public Set<String> getState() {
        if(!connected) {
            // Just return an empty Set if we're not connected
            return new HashSet<>();
        }

        Set<String> internalState = new HashSet<>();

        GamePadButton[] digitalButtons = GamePadButton.values();

        for(GamePadButton button : digitalButtons) {
            if(isDown(button)) {
                internalState.add(XInput.Defaults.get(playerIndex, button));
            }
        }

        return internalState;
    }

    //todo
    private void checkJoySticks() {
        int leftThumb = (int)currentState.XInputGamepad.ThumbLX;

        System.out.println(leftThumb);
    }

    // Check if a digital button is being pressed
    private boolean isDown(GamePadButton button) {
        return (currentState.XInputGamepad.Buttons & button.getBitmask()) == button.getBitmask();
    }
}
