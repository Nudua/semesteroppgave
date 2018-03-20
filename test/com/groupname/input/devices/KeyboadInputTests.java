package com.groupname.input.devices;
import com.groupname.framework.input.devices.KeyboardInput;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KeyboadInputTests {

    // This is required because we're not allowed to create a scene unless it's on a javafx thread
    private class FXTestApp extends Application {
        private Scene scene = new Scene(new GridPane());

        @Override
        public void start(Stage primaryStage) {
        }
    }

    private FXTestApp app = new FXTestApp();
    private Scene scene;

    @Before
    public void before() {
        FXTestApp app = new FXTestApp();
        scene = app.scene;
    }

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
