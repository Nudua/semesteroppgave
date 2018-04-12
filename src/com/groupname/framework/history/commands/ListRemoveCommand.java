package com.groupname.framework.history.commands;

import java.util.List;

public class ListRemoveCommand<E> extends ListCommand<E> {

    public ListRemoveCommand(List<E> list, E item) {
        super(list, item);
    }

    @Override
    public void execute() {
        list.remove(item);
    }

    @Override
    public void undo() {
        list.add(item);
    }

    /**
     * The name of the Command.
     *
     * @return the name.
     */
    @Override
    public String getName() {
        return "ListRemoveCommand";
    }
}
