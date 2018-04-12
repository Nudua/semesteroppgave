package com.groupname.framework.history.commands;

import java.util.List;

public class ListAddCommand<E> extends ListCommand<E> {

    public ListAddCommand(List<E> list, E item) {
        super(list, item);
    }

    @Override
    public void execute() {
        list.add(item);
    }

    @Override
    public void undo() {
        list.remove(item);
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
