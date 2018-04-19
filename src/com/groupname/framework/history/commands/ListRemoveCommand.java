package com.groupname.framework.history.commands;

import java.util.List;

/**
 * An implementation of the ListCommand<E> class, this class can be
 * used to remove items from a list and allows you to undo the by re-adding it.
 *
 * @param <E> the class type used by the List.
 */
public class ListRemoveCommand<E> extends ListCommand<E> {

    /**
     * Creates a new instance with the specified list and item to remove from the list.
     *
     * @param list the list to remove items from, or re-add via the undo command.
     * @param item the item that should be removed.
     */
    public ListRemoveCommand(List<E> list, E item) {
        super(list, item);
    }

    /**
     * Removes the specified <E> item from the specified list.
     */
    @Override
    public void execute() {
        list.remove(item);
    }

    /**
     * Adds the item back into the list.
     */
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
