package com.groupname.framework.input.devices;

import java.util.Set;


/**
 * Interface that collects the different types of inputs.
 * The implementations must implements all the methods.
 */

public interface InputAdapter {

    boolean isEnabled();

    /**
     * The implementations must use this method to enable or disable the InputAdapter.
     *
     * @param enabled true or false.
     */
    void setEnabled(boolean enabled);

    /**
     * The implementations must use this method to update the specified set with the internal currently pressed input.
     *
     * @param digitalInput the collection to add the currently pressed input.
     */
    void update(Set<String> digitalInput);
}
