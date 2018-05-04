package com.groupname.game.entities;

import com.groupname.framework.io.Content;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SpriteFactoryTests {

    @BeforeClass
    public static void init() {
        Content.setContentBaseFolder("/com/groupname/game/resources");
    }


    @Test(expected = NullPointerException.class)
    public void createEnemyParameterCannotBeNull() {
        new SpriteFactory().createEnemy(null);
    }

    @Test
    public void createEnemyReturnNotNull() {
       assertNotNull(new SpriteFactory().createEnemy(EnemySpriteType.BEE));
    }


    @Test
    public void createPlayerReturnNotNull() {
        assertNotNull(new SpriteFactory().createPlayer());
    }

    @Test
    public void createProjectileReturnNotNull() {
        assertNotNull(new SpriteFactory().createProjectile());
    }


}
