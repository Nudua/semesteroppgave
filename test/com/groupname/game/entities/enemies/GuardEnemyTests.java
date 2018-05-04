package com.groupname.game.entities.enemies;

import com.groupname.framework.io.Content;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.EnemySpriteType;
import com.groupname.game.entities.SpriteFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GuardEnemyTests {


    @BeforeClass
    public static void init() {
        Content.setContentBaseFolder("/com/groupname/game/resources");
    }

    @Test(expected = NullPointerException.class)
    public void constructorParameterCannotMeNull() {
        new GuardEnemy(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void positionCannotBeNull() {
        new GuardEnemy(new SpriteFactory().createEnemy(EnemySpriteType.BEE), new Vector2D(2,2)).setPosition(null);
    }

    @Test
    public void setPositionWorks() {
        GuardEnemy enemy = new GuardEnemy(new SpriteFactory().createEnemy(EnemySpriteType.BEE), new Vector2D(3,3));
        enemy.setPosition(new Vector2D(1,2));
        assertEquals((int)enemy.getPosition().getX(), (int)1);
        assertEquals((int)enemy.getPosition().getY(), (int)2);

    }

    @Test(expected = NullPointerException.class)
    public void drawNotNull() {
        GuardEnemy enemy = new GuardEnemy(new SpriteFactory().createEnemy(EnemySpriteType.BEE), new Vector2D(3,3));
        enemy.draw(null);
    }
}
