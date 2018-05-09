package com.groupname.framework.input.xinput;

import com.groupname.framework.input.devices.InputAdapter;
import com.groupname.framework.input.xinput.interop.GamePadButton;
import com.groupname.framework.input.xinput.interop.XInputLibrary;
import com.groupname.framework.input.xinput.interop.XInputLibraryFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class handles polling from XInput based gamepads.
 *
 * 4 Controllers are supported at the same time.
 */
public class XInput implements InputAdapter {
    private boolean enabled = true;
    private final List<GamePad> gamePads;

    private boolean libraryLoaded;
    private XInputLibrary xInputLibrary;

    /**
     * Creates a new instance of this class.
     */
    public XInput() {
        gamePads = new ArrayList<>();
    }

    /**
     * Attempts to load the required library needed to run this class
     * and creates 4 new gamepads for polling (if they are connected).
     *
     * @throws LibraryNotFoundException if it was not possible to load the XInput DLL.
     */
    public void initialize() throws LibraryNotFoundException {
        xInputLibrary = XInputLibraryFactory.create();

        if(xInputLibrary != null) {
            libraryLoaded = true;
        } else {
            throw new LibraryNotFoundException("XInput DLL not found");
        }

        gamePads.add(new GamePad(PlayerIndex.ONE));
        gamePads.add(new GamePad(PlayerIndex.TWO));
        gamePads.add(new GamePad(PlayerIndex.THREE));
        gamePads.add(new GamePad(PlayerIndex.FOUR));
    }

    /**
     * Returns true if this inputAdapter is enabled.
     *
     * @return true if this inputAdapter is enabled.
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether this inputadapter should be enabled or not.
     *
     * @param enabled whether this inputadapter should be enabled or not.
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Polls all the connected XInput gamepads and puts the state of all the buttons into the specified set.
     *
     * @param digitalInput the collection to add the currently pressed inputs.
     */
    @Override
    public void update(Set<String> digitalInput) {
        if(!libraryLoaded || !enabled) {
            return;
        }

        for(GamePad gamePad : gamePads) {
            // Poll every connected GamePad to get their most recent state
            gamePad.poll(xInputLibrary);

            // Then get the internal state
            Set<String> state = gamePad.getState();

            // Add it to the unified set
            digitalInput.addAll(state);
        }
    }

    /**
     * Helper method that converts the PlayerIndex (enum) and GamePadButton into it's string representation.
     *
     * @param playerIndex the controller index to use.
     * @param gamePadButton the button to convert to a string.
     * @return the PlayerIndex (enum) and GamePadButton as a string representation.
     */
    public static String getButtonAsString(PlayerIndex playerIndex, GamePadButton gamePadButton) {
        return String.format("XInput Controller %d - %s", playerIndex.getIndex() + 1, gamePadButton.name());
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "XInput{" +
                "enabled=" + enabled +
                ", gamePads=" + gamePads +
                ", libraryLoaded=" + libraryLoaded +
                ", xInputLibrary=" + xInputLibrary +
                '}';
    }
}
