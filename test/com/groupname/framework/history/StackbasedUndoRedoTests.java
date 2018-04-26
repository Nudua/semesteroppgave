package com.groupname.framework.history;

import com.groupname.framework.history.commands.Command;
import com.groupname.framework.history.commands.ListAddCommand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StackbasedUndoRedoTests {

    @Test
    public void executeRunsCommand() {
        UndoRedo undoRedo = new StackBasedUndoRedo();

        List<String> list = new ArrayList<>();
        String item1 = "item1";

        undoRedo.execute(new ListAddCommand<>(list, item1));

        assertTrue(list.contains(item1));
    }

    @Test
    public void canUndoIsConsistent() {
        UndoRedo undoRedo = new StackBasedUndoRedo();

        List<String> list = new ArrayList<>();
        String item1 = "item1";

        undoRedo.execute(new ListAddCommand<>(list, item1));

        assertTrue(undoRedo.canUndo());
    }

    @Test
    public void canRedoIsConsistent() {
        UndoRedo undoRedo = new StackBasedUndoRedo();

        List<String> list = new ArrayList<>();
        String item1 = "item1";

        undoRedo.execute(new ListAddCommand<>(list, item1));

        undoRedo.undo();

        assertTrue(undoRedo.canRedo());
    }

    @Test
    public void undoWorks() {
        UndoRedo undoRedo = new StackBasedUndoRedo();

        List<String> list = new ArrayList<>();
        String item1 = "item1";

        undoRedo.execute(new ListAddCommand<>(list, item1));

        assertTrue(list.contains(item1));

        undoRedo.undo();
    }

    @Test
    public void redoWorks() {
        UndoRedo undoRedo = new StackBasedUndoRedo();

        List<String> list = new ArrayList<>();
        String item1 = "item1";

        // Adds item to the list
        undoRedo.execute(new ListAddCommand<>(list, item1));
        assertTrue(list.contains(item1));

        // Removes item from the list.
        undoRedo.undo();
        assertFalse(list.contains(item1));

        // Adds the item back into the list.
        undoRedo.redo();
        assertTrue(list.contains(item1));
    }

}
