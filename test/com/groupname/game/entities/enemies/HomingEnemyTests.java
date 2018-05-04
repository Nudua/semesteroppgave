package com.groupname.game.entities.enemies;

import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.editor.metadata.LevelFactory;
import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.entities.EnemySpriteType;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SpriteFactory;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import org.junit.BeforeClass;
import org.junit.Test;
import test.util.MockFX;

import static org.junit.Assert.*;

public class HomingEnemyTests {

    @BeforeClass
    public static void init() {
        MockFX.initFX();
        Content.setContentBaseFolder("/com/groupname/game/resources");
    }
    private Scene scene = new Scene(new GridPane());


    @Test(expected = NullPointerException.class)
    public void playerParameterCannotBeNull() {
        new HomingEnemy(new SpriteFactory().createEnemy(EnemySpriteType.SNAIL), new Vector2D(1,1), null);
    }

    @Test(expected = NullPointerException.class)
    public void drawNotNull() {
        LevelFactory levelFactory = new LevelFactory(new InputManager(scene));
        Player player = (Player) levelFactory.create(new ObjectMetaData("Player", Player.class));
        HomingEnemy enemy = new HomingEnemy(new SpriteFactory().createEnemy(EnemySpriteType.BEE), new Vector2D(3,3), player);
        enemy.draw(null);
    }

}
