package com.groupname.framework.history.commands;

import java.util.List;
import java.util.Objects;

/**
 * This command is used to add or remove a selected item to a List.
 *
 * @param <E> the type of elements to add to the list.
 */
public class ListAddCommand<E> implements Command {
    private List<E> list;
    private E item;

    /**
     * Creates a new instance of the ListAddCommand class with the specified type of list and item.
     *
     * @param list the collection to add or remove the item.
     * @param item the item to add or remove from the List.
     */
    public ListAddCommand(List<E> list, E item) {
        this.list = Objects.requireNonNull(list);
        this.item = Objects.requireNonNull(item);
    }

    /**
     * The name of the Command.
     *
     * @return the name.
     */
    @Override
    public String getName() {
        return "ListAdd";
    }

    /**
     *  Adds the item supplied in the constructor to the list specified.
     */
    @Override
    public void execute() {
        if(item instanceof String) {
            System.out.println("Adding:" + item);
        }

        list.add(item);
    }

    /**
     *  Removes the item supplied in the constructor from the list specified.
     */
    @Override
    public void undo() {
        if(item instanceof String) {
            System.out.println("Removing:" + item);
        }

        list.remove(item);
    }
}
