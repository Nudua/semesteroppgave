package com.groupname.game.editor.metadata;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SpriteFactory;
import com.groupname.game.entities.enemies.HomingEnemy;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import org.junit.BeforeClass;
import org.junit.Test;
import test.util.MockFX;

import java.security.InvalidParameterException;

import static org.junit.Assert.*;

public class LevelObjectFactoryTests {

    @BeforeClass
    public static void init() {
        // For these tests we have to make sure that the Javafx thread is initialized
        MockFX.initFX();
        Content.setContentBaseFolder("/com/groupname/game/resources");

    }

    private Scene scene = new Scene(new GridPane());

    @Test(expected = NullPointerException.class)
    public void constructorParameterCannotBeNull() {
        new LevelObjectFactory(null);
    }

    @Test(expected = NullPointerException.class)
    public void setPlayerParameterCannotBeNull() {
        InputManager inputManager = new InputManager(scene);

        LevelObjectFactory levelObjectFactory = new LevelObjectFactory(inputManager);
        levelObjectFactory.setPlayer(null);

    }

    @Test(expected = InvalidParameterException.class)
    public void createGameObjectParameterMustBeSupported() {
        InputManager inputManager = new InputManager(scene);

        LevelObjectFactory levelObjectFactory = new LevelObjectFactory(inputManager);
        ObjectMetaData metadata = new ObjectMetaData("Test", Vector2D.class);

        levelObjectFactory.create(metadata);
    }
}
