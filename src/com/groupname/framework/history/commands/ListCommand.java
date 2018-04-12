package com.groupname.framework.history.commands;

import java.util.List;
import java.util.Objects;

/**
 * This command is used to add or remove a selected item to a List.
 *
 * @param <E> the type of elements to add to the list.
 */
public abstract class ListCommand<E> implements Command {
    protected List<E> list;
    protected E item;

    /**
     * Creates a new instance of the ListAddCommand class with the specified type of list and item.
     *
     * @param list the collection to add or remove the item.
     * @param item the item to add or remove from the List.
     */
    public ListCommand(List<E> list, E item) {
        this.list = Objects.requireNonNull(list);
        this.item = Objects.requireNonNull(item);
    }
}

