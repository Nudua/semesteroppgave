package com.groupname.game.entities.enemies;

import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.editor.metadata.LevelObjectFactory;
import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.entities.EnemySpriteType;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SpriteFactory;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import org.junit.BeforeClass;
import org.junit.Test;
import test.util.MockFX;

public class BossEnemyTests {

    @BeforeClass
    public static void init() {
        MockFX.initFX();
        Content.setContentBaseFolder("/com/groupname/game/resources");
    }
    private Scene scene = new Scene(new GridPane());

    @Test(expected = NullPointerException.class)
    public void playerParameterCannotBeNull() {
        new BossEnemy(new SpriteFactory().createEnemy(EnemySpriteType.BLUE_BLOB), new Vector2D(1,1), null);
    }

    @Test(expected = NullPointerException.class)
    public void positionParameterCannotBeNull() {
        LevelObjectFactory levelObjectFactory = new LevelObjectFactory(new InputManager(scene));
        Player player = (Player) levelObjectFactory.create(new ObjectMetaData("Player", Player.class));
        new BossEnemy(new SpriteFactory().createEnemy(EnemySpriteType.BLUE_BLOB), null, player);
    }

    @Test(expected = NullPointerException.class)
    public void drawParameterCannotBeNull() {
        LevelObjectFactory levelObjectFactory = new LevelObjectFactory(new InputManager(scene));
        Player player = (Player) levelObjectFactory.create(new ObjectMetaData("Player", Player.class));
        BossEnemy enemy = new BossEnemy(new SpriteFactory().createEnemy(EnemySpriteType.BEE), new Vector2D(1,1), player);

        enemy.draw(null);
    }


}
