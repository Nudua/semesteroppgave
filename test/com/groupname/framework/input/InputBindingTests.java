package com.groupname.framework.input;

import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.util.EmptyStringException;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class InputBindingTests {

    private String validName = "Bob";
    private String[] validBindings = new String[] { "test1", "test2" };

    @Test(expected = NullPointerException.class)
    public void nameCannotBeNullDefaultConstructor() {
        new InputBinding(null);
    }

    @Test(expected = EmptyStringException.class)
    public void nameCannotBeEmptyDefaultConstructor() {
        new InputBinding("");
    }

    @Test(expected = NullPointerException.class)
    public void nameCannotBeNullOverload() {
        new InputBinding(null, validBindings);
    }

    @Test(expected = EmptyStringException.class)
    public void nameCannotBeEmptyOverload() {
        new InputBinding("", validBindings);
    }

    @Test
    public void getNameGivesCorrectName() {
        InputBinding binding1 = new InputBinding(validName);
        InputBinding binding2 = new InputBinding(validName, validBindings);

        assertEquals(binding1.getName(), validName);
        assertEquals(binding2.getName(), validName);
    }

    @Test
    public void constructorAddsBindings() {
        InputBinding binding = new InputBinding(validName, validBindings);

        Set<String> allBindings = binding.getBindings();

        for(int i = 0; i < validBindings.length; i++) {
            assertTrue(allBindings.contains(validBindings[i]));
        }
    }

    @Test
    public void addBindingAddsBinding() {
        InputBinding binding = new InputBinding(validName);

        String myButton = "button1";

        binding.addBinding(myButton);

        Set<String> bindings = binding.getBindings();

        assertTrue(bindings.contains(myButton));
    }

    @Test
    public void getBindingsIsNotNull() {
        InputBinding binding = new InputBinding(validName);

        assertNotNull(binding.getBindings());
    }
}
