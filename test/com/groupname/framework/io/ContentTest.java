package com.groupname.framework.io;

import com.groupname.framework.util.EmptyStringException;
import org.junit.Test;


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
