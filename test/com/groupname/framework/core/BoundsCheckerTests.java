package com.groupname.framework.core;

import com.groupname.framework.io.Content;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.core.BoundsChecker;
import com.groupname.game.entities.EnemySpriteType;
import com.groupname.game.entities.SpriteFactory;
import com.groupname.game.entities.enemies.GuardEnemy;
import javafx.scene.shape.Rectangle;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoundsCheckerTests {

    @BeforeClass
    public static void init() {
        Content.setContentBaseFolder("/com/groupname/game/resources");
    }

    @Test
    public void checkIsWithinBounds() {
        BoundsChecker boundsChecker = new BoundsChecker();
        SpriteFactory spriteFactory = new SpriteFactory();

        GuardEnemy object = new GuardEnemy((spriteFactory.createEnemy(EnemySpriteType.Bee)), new Vector2D(100,100));

        assertTrue(boundsChecker.isWithinBounds(object, new Rectangle(1000,1000)));
    }

    @Test
    public void checkIsNotWithinBounds() {
        BoundsChecker boundsChecker = new BoundsChecker();
        SpriteFactory spriteFactory = new SpriteFactory();

        GuardEnemy object = new GuardEnemy((spriteFactory.createEnemy(EnemySpriteType.Bee)), new Vector2D(100,100));

        assertFalse(boundsChecker.isWithinBounds(object, new Rectangle(1,1)));
    }
}
