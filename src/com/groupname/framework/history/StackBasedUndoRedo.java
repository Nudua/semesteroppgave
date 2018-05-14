package com.groupname.framework.history;

import com.groupname.framework.history.commands.Command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A stack based implementation of the UndoRedo interface.
 * Made for executing, undoing and redoing commands.
 */
public class StackBasedUndoRedo implements UndoRedo {
    // The internal collections used for this implementation.
    private Deque<Command> undoStack = new ArrayDeque<>();
    private Deque<Command> redoStack = new ArrayDeque<>();

    /**
     * Creates a new instance of this class.
     */
    public StackBasedUndoRedo() {
    }

    /**
     * Executes the command specified.
     *
     * @param command the item to execute.
     */
    @Override
    public void execute(Command command) {
        command.execute();
        undoStack.push(command);

        // Clear the redo stack
        redoStack.clear();
    }

    /**
     * Attempts to redo the last undone command.
     */
    @Override
    public void redo() {
        if(canRedo()) {
            Command lastCommand = redoStack.pop();
            lastCommand.execute();

            undoStack.push(lastCommand);
        }
    }

    /**
     * Attempts to undo the last command executed.
     */
    @Override
    public void undo() {
        if(canUndo()) {
            Command lastCommand = undoStack.pop();
            lastCommand.undo();

            redoStack.push(lastCommand);
        }
    }

    /**
     * Checks if it's possible to undo the last command executed.
     *
     * @return true if you can undo, false otherwise.
     */
    @Override
    public boolean canUndo() {
        return undoStack.size() > 0;
    }

    /**
     * Checks if it's possible to redo the last undone command.
     *
     * @return true if you can redo, false otherwise.
     */
    @Override
    public boolean canRedo() {
        return redoStack.size() > 0;
    }

    /**
     * Returns the contents of this instance as a String, which is the undo and redo stacks and their commands if any.
     *
     * @return the contents of this instance as a String, which is the undo and redo stacks and their commands if any.
     */
    @Override
    public String toString() {
        return "StackBasedUndoRedo{" +
                "undoStack=" + undoStack +
                ", redoStack=" + redoStack +
                '}';
    }
}
