package com.groupname.game.entities;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.powerups.HeartPowerUp;
import org.junit.Test;
import static org.junit.Assert.*;


public class HeartPowerUpTests {

    SpriteSheet testSheet = new SpriteSheet("spriteSheet", Content.loadImage("player1.png", ResourceType.SpriteSheet));
    Sprite testSprite = new Sprite(testSheet, Sprite.createSpriteRegion(66,66));

    @Test(expected = NullPointerException.class)
    public void heartCannotBeNegative() {
        HeartPowerUp testHeart = new HeartPowerUp(testSprite, new Vector2D(),-1);
    }

    @Test(expected = NullPointerException.class)
    public void spriteCannotBeNull() {
        HeartPowerUp testHeart = new HeartPowerUp(null, new Vector2D(),2);
    }

    @Test(expected = NullPointerException.class)
    public void positionCannotBeNull() {
        HeartPowerUp testHeart = new HeartPowerUp(testSprite, null,2);
    }

    @Test
    public void isCollectedFalseByDefault() {
        HeartPowerUp testHeart = new HeartPowerUp(testSprite, new Vector2D(),2);
        assertFalse(testHeart.isCollected());
    }

    //TODO
    @Test
    public void trueWhenIsCollected(){
    }







}
