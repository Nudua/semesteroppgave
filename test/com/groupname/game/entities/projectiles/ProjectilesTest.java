package com.groupname.game.entities.projectiles;

import com.groupname.framework.io.Content;
import com.groupname.game.entities.SpriteFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProjectilesTest {

    @BeforeClass
    public static void init() {
        Content.setContentBaseFolder("/com/groupname/game/resources");
    }


    @Test(expected = NullPointerException.class)
    public void spriteParameterCannotBeNull() {
        new Projectile(null);
    }


    @Test
    public void isAliveFalseByDefaultCheck() {
        Projectile projectile = new Projectile(new SpriteFactory().createProjectile());
        assertFalse(projectile.isAlive());

    }

    @Test
    public void setAliveCheck() {
        Projectile projectile = new Projectile(new SpriteFactory().createProjectile());
        projectile.setAlive(true);

        assertTrue(projectile.isAlive());
    }

    @Test(expected = NullPointerException.class)
    public void drawsParametreCannotBeNull() {
        Projectile projectile = new Projectile(new SpriteFactory().createProjectile());
        projectile.draw(null);
    }



}
