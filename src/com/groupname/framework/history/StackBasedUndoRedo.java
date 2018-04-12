package com.groupname.framework.history;



import com.groupname.framework.history.commands.Command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A stack based implementation of the UndoRedo interface.
 * Made for executing, undoing and redoing commands.
 */
public class StackBasedUndoRedo implements UndoRedo {

    // Make my own limit based stack implementation
    private Deque<Command> undoStack = new ArrayDeque<>();
    private Deque<Command> redoStack = new ArrayDeque<>();

    /**
     * Executes the command specified.
     *
     * @param command the item to execute.
     */
    @Override
    public void execute(Command command) {
        command.execute();
        undoStack.push(command);

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
}
