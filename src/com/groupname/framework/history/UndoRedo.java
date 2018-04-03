package com.groupname.framework.history;


import com.groupname.framework.history.commands.Command;

/**
 * An interface for executing, undoing and redoing commands.
 */
public interface UndoRedo {

    /**
     * Executes the command specified.
     *
     * @param command the item to execute.
     */
    void execute(Command command);

    /**
     * Attempts to redo the last undone command.
     */
    void redo();

    /**
     * Attempts to undo the last command executed.
     */
    void undo();

    /**
     * Checks if it's possible to undo the last command executed.
     *
     * @return true if you can undo, false otherwise.
     */
    boolean canUndo();

    /**
     * Checks if it's possible to redo the last undone command.
     *
     * @return true if you can redo, false otherwise.
     */
    boolean canRedo();
}
