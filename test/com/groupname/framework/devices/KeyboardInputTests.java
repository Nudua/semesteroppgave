package com.groupname.framework.devices;
import com.groupname.framework.input.devices.KeyboardInput;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/*
class MockGameApplication extends Application {

    private static MockGameApplication INSTANCE;

    public static MockGameApplication get() {
        if (INSTANCE == null) {
            mockStage();

            MockGameApplication app = new MockGameApplication();

            INSTANCE = app;
        }

        return INSTANCE;
    }

    private static void mockStage() {
        new Thread(() -> {
            Application.launch(MockGameApplication.class);
        }).start();
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
*/

public class KeyboardInputTests {

    // Todo: fix on windows
    private Scene scene = new Scene(new GridPane());

    @Before
    public void before() {

    }

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
