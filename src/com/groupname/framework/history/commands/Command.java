package com.groupname.framework.history.commands;

/**
 * Represents a command that is used to execute and undo a method.
 */
public interface Command {
    /**
     * The name of the Command.
     *
     * @return the name.
     */
    String getName();

    /**
     *  This method gets called when the command gets executed.
     *  Also used for redoing a command.
     */
    void execute();

    /**
     * This method is used to undo all the actions of the execute method.
     */
    void undo();
}



