package com.groupname.framework.input.xinput;

import com.groupname.framework.input.devices.InputAdapter;
import com.groupname.framework.input.xinput.interop.GamePadButton;
import com.groupname.framework.input.xinput.interop.XInputLibrary;
import com.groupname.framework.input.xinput.interop.XInputLibraryFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class XInput implements InputAdapter {

    public static final int DEFAULT_JOYSTICK_DEADZONE = 7849;

    private boolean enabled = true;
    private final List<GamePad> gamePads;

    private boolean libraryLoaded;
    private XInputLibrary xInputLibrary;

    public XInput() {
        gamePads = new ArrayList<>();
    }

    public void initialize() throws LibraryNotFoundException {
        xInputLibrary = XInputLibraryFactory.create();

        if(xInputLibrary != null) {
            libraryLoaded = true;
        } else {
            throw new LibraryNotFoundException("Dll not found");
        }

        gamePads.add(new GamePad(PlayerIndex.One));
        gamePads.add(new GamePad(PlayerIndex.Two));
        gamePads.add(new GamePad(PlayerIndex.Three));
        gamePads.add(new GamePad(PlayerIndex.Four));
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void update(Set<String> digitalInput) {
        if(!libraryLoaded) {
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

    public static class Defaults {
        public static String get(PlayerIndex playerIndex, GamePadButton gamePadButton) {
            return String.format("XInput Controller %d - %s", playerIndex.getIndex() + 1, gamePadButton.name());
        }
    }
}
