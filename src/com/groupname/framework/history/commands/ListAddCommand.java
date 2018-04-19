package com.groupname.framework.history.commands;

import java.util.List;

/**
 * An implementation of the ListCommand<E> class, this class can be
 * used to add items from a list and allows you to undo the by removing it from the list.
 *
 * @param <E> the class type used by the List.
 */
public class ListAddCommand<E> extends ListCommand<E> {

    /**
     * Creates a new instance with the specified list and item to add to the list.
     *
     * @param list the list to add items to, or remove via the undo command.
     * @param item the item that gets added or removed.
     */
    public ListAddCommand(List<E> list, E item) {
        super(list, item);
    }

    /**
     * Adds the item specified in the constructor to the list.
     */
    @Override
    public void execute() {
        list.add(item);
    }

    /**
     * Undo's the execute command, by removing the item from the list.
     */
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
