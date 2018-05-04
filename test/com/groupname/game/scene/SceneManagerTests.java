package com.groupname.game.scene;

import org.junit.Test;
import test.util.MockFX;

import static org.junit.Assert.*;

public class SceneManagerTests {

    @Test(expected = NullPointerException.class)
    public void primaryStageCannotBeNull() {
        SceneManager.INSTANCE.setPrimaryStage(null);
    }

    @Test(expected = NullPointerException.class)
    public void sceneNameCannotBeNull() {
        SceneManager.navigate(null);
    }
}
