package com.groupname.framework.devices;
import com.groupname.framework.input.devices.KeyboardInput;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.util.MockFX;

import static org.junit.Assert.*;

public class KeyboardInputTests {

    @BeforeClass
    public static void init() {
        // For these tests we have to make sure that the Javafx thread is initialized
        MockFX.initFX();
    }

    // Todo: fix on windows
    private Scene scene = new Scene(new GridPane());

    @Test(expected = NullPointerException.class)
    public void checkSceneNotNull() {
        new KeyboardInput(null);
    }

    @Test
    public void checkKeyboardInputIsEnableByDefault() {
        KeyboardInput keyboardInput = new KeyboardInput(scene);
        assertTrue(keyboardInput.isEnabled());
    }

    @Test
    public void checkKeybordInputSetEnableToFalse() {
        KeyboardInput keyboardInput = new KeyboardInput(scene);
        keyboardInput.setEnabled(false);

        assertFalse(keyboardInput.isEnabled());
    }

    @Test(expected = NullPointerException.class)
    public void checkDigitalInputCannotBeNull() {
        KeyboardInput keyboardInput = new KeyboardInput(scene);
        keyboardInput.update(null);
    }
}
