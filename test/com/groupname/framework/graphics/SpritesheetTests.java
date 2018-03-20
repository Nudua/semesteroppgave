package com.groupname.framework.graphics;

import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.util.EmptyStringException;
import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Test;

public class SpritesheetTests {

    private Image img = Content.loadImage("projectiles.png", ResourceType.SpriteSheet);

    @Test(expected = NullPointerException.class)
    public void nameCannotBeNull() {
        SpriteSheet spriteSheet = new SpriteSheet(null, img);
    }

    @Test(expected = EmptyStringException.class)
    public void nameCannotBeEmpty() {
        SpriteSheet spriteSheet = new SpriteSheet("", img);
    }

    @Test(expected = NullPointerException.class)
    public void imageCannotBeNull(){
        SpriteSheet spriteSheet = new SpriteSheet("Tom", null);
    }

    @Test
    public void imageGetSet() {
        SpriteSheet spritesheet = new SpriteSheet("hei", img);
        //Assert.assertTrue(spritesheet.getImage() == img);
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
