package com.groupname.framework.input;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import org.junit.BeforeClass;
import test.util.MockFX;

import org.junit.Test;

import java.rmi.server.ExportException;

import static org.junit.Assert.*;

public class InputManagerTests {
    @BeforeClass
    public static void init() {
        // For these tests we have to make sure that the Javafx thread is initialized
        MockFX.initFX();
    }

    private Scene scene = new Scene(new GridPane());

    @Test(expected = NullPointerException.class)
    public void checkConstructorParameterNotNull() {
        InputManager inputManager = new InputManager(null);
    }

    @Test(expected = NullPointerException.class)
    public void checkUpdateSceneParameterNotNull() {
        InputManager inputManager = new InputManager(scene);
        inputManager.updateScene(null);

    }

    @Test
    public void enableWorksCorrectly() {
        InputManager inputManager = new InputManager(scene);

        inputManager.setEnabled(true);
        assertTrue(inputManager.isEnabled());

        inputManager.setEnabled(false);
        assertFalse(inputManager.isEnabled());
    }

}
