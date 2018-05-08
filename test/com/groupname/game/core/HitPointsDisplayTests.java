package com.groupname.game.core;

import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.game.editor.metadata.LevelObjectFactory;
import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.entities.Player;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import jdk.internal.util.xml.impl.Input;
import org.junit.BeforeClass;
import org.junit.Test;
import test.util.MockFX;


public class HitPointsDisplayTests {

    @BeforeClass
    public static void init() {
        MockFX.initFX();
        Content.setContentBaseFolder("/com/groupname/game/resources");
    }
    private Scene scene = new Scene(new GridPane());


    @Test(expected = NullPointerException.class)
    public void constructorParameterCannotBeNull() {
        new HitPointsDisplay(null);
    }

    @Test(expected = NullPointerException.class)
    public void drawParameterCannotBeNull() {
        LevelObjectFactory levelObjectFactory = new LevelObjectFactory(new InputManager(scene));
        Player player = (Player) levelObjectFactory.create(new ObjectMetaData("Player", Player.class));

        HitPointsDisplay hitPointsDisplay = new HitPointsDisplay(player);
        hitPointsDisplay.draw(null);
    }
}
