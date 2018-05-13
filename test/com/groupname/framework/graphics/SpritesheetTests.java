package com.groupname.framework.graphics;

import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.util.EmptyStringException;
import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Test;

public class SpritesheetTests {

    private Image img = Content.loadImage("projectiles.png", ResourceType.SPRITE_SHEET);

    @Test(expected = NullPointerException.class)
    public void nameCannotBeNull() {
        new SpriteSheet(null, img);
    }

    @Test(expected = EmptyStringException.class)
    public void nameCannotBeEmpty() {
        new SpriteSheet("", img);
    }

    @Test(expected = NullPointerException.class)
    public void imageCannotBeNull(){
        new SpriteSheet("Tom", null);
    }

    @Test
    public void imageGetIsSame() {
        SpriteSheet spritesheet = new SpriteSheet("hei", img);
        Assert.assertSame(spritesheet.getImage(), img);
    }

    @Test
    public void nameGetSetToConstructorParameter() {
        String name = "Tom";
        SpriteSheet spriteSheet = new SpriteSheet(name, img);
        Assert.assertEquals(name, spriteSheet.getName());
    }

    @Test
    public void widthAndHeightGetSet(){
        SpriteSheet spriteSheet = new SpriteSheet("Tom", img);
        Assert.assertEquals((int)img.getWidth(), spriteSheet.getWidth());
        Assert.assertEquals((int)img.getHeight(), spriteSheet.getHeight());
    }

}
