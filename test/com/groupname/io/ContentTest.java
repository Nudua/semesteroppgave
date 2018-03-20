package com.groupname.io;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.util.EmptyStringException;
import org.junit.Test;

import java.nio.channels.NotYetBoundException;

import static org.junit.Assert.*;


public class ContentTest {

    private String player = "player1.png";

    @Test(expected = NullPointerException.class)
    public void nameCannotBeNull(){
        Content.loadFile(null,ResourceType.Sprite);
    }

    @Test(expected = EmptyStringException.class)
    public void nameCannotBeEmpty(){
        Content.loadFile("",ResourceType.Sprite);
    }

    @Test(expected = NullPointerException.class)
    public void typeCannotBeEmpty(){
        Content.loadFile(player,null);
    }


    @Test(expected = NullPointerException.class)
    public void loadNameCannotBeNull(){
        Content.loadImage(null,ResourceType.Sprite);
    }

    @Test(expected = EmptyStringException.class)
    public void loadNameCannotBeEmpty(){
        Content.loadImage("",ResourceType.Sprite);
    }

    @Test(expected = IllegalArgumentException.class)
    public void loadTypeCannotBeEmpty(){
        Content.loadImage(player,null);
    }

}
