package com.groupname.input.devices;
import com.groupname.framework.input.devices.KeyboardInput;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;

public class KeyboadInputTests {

    Scene scene = new Scene(new GridPane());

    @Test(expected = NullPointerException.class)
    public void checkSceneNotNull() {
        KeyboardInput keyboardInput = new KeyboardInput(null);
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

        assertTrue(keyboardInput.isEnabled() == false);
    }

    @Test(expected = NullPointerException.class)
    public void checkDigitalInputCannotBeNull() {
        KeyboardInput keyboardInput = new KeyboardInput(scene);
        keyboardInput.update(null);
    }
}
