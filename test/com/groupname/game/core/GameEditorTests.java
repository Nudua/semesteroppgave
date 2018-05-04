package com.groupname.game.core;

import com.groupname.framework.history.StackBasedUndoRedo;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.editor.LevelItem;
import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.entities.EnemySpriteType;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SpriteFactory;
import com.groupname.game.entities.enemies.GuardEnemy;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import org.junit.BeforeClass;
import test.util.MockFX;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GameEditorTests {

    @BeforeClass
    public static void init() {
        // For these tests we have to make sure that the Javafx thread is initialized
        MockFX.initFX();
    }

    private Scene scene = new Scene(new GridPane());

    // Constructor tests
    @Test(expected = NullPointerException.class)
    public void gameCannotBeNull() {
        new GameEditor(null, new Canvas(), new ArrayList<>(), new StackBasedUndoRedo());
    }

    @Test(expected = NullPointerException.class)
    public void canvasCannotBeNull() {
        Game game = new Game(scene, 100, 100);

        new GameEditor(game, null, new ArrayList<>(), new StackBasedUndoRedo());
    }

    @Test(expected = NullPointerException.class)
    public void levelItemsCannotBeNull() {
        Game game = new Game(scene, 100, 100);

        new GameEditor(game, new Canvas(), null, new StackBasedUndoRedo());
    }

    @Test(expected = NullPointerException.class)
    public void undoRedoCannotBeNull() {
        Game game = new Game(scene, 100, 100);

        GameEditor editor = new GameEditor(game, new Canvas(), new ArrayList<>(), null);
    }

    // setMode
    @Test(expected = NullPointerException.class)
    public void modeCannotBeNull() {
        Game game = new Game(scene, 100, 100);

        /*
        SpriteFactory spriteFactory = new SpriteFactory();

        GuardEnemy enemy = new GuardEnemy(spriteFactory.createEnemy(EnemySpriteType.BEE), new Vector2D());

        LevelItem item = new LevelItem(new ObjectMetaData("enemy", Player.class), enemy);
        */

        GameEditor editor = new GameEditor(game, new Canvas(), new ArrayList<>(), new StackBasedUndoRedo());

        editor.setMode(null);
    }


}
