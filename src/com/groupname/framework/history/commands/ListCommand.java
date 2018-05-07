package com.groupname.framework.history.commands;

import java.util.List;
import java.util.Objects;

/**
 * The base class for doing operations on a List via the Command interface.
 *
 * @param <E> the class type of elements to add to the list.
 * @see ListAddCommand<E>
 * @see ListRemoveCommand<E>
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

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "ListCommand{" +
                "list=" + list +
                ", item=" + item +
                '}';
    }
}

