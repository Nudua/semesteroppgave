package com.groupname.game.entities.projectiles;

import com.groupname.framework.io.Content;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SingleBulletWeaponTests {

    @BeforeClass
    public static void init() {
        Content.setContentBaseFolder("/com/groupname/game/resources");
    }

    @Test(expected = NullPointerException.class)
    public void firePositionCannotBeNull() {
       SingleBulletWeapon test = new SingleBulletWeapon(1,2);
       test.fire(null,Direction.RIGHT);

    }

    @Test(expected = NullPointerException.class)
    public void fireDirectionCannotBeNull() {
        SingleBulletWeapon test = new SingleBulletWeapon(1,2);
        test.fire(new Vector2D(2,2),null);
    }

    @Test(expected = NullPointerException.class)
    public void drawParameterCannotBeNull() {
        SingleBulletWeapon test = new SingleBulletWeapon(1,2);
        test.draw(null);
    }



}
