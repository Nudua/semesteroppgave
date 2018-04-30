package com.groupname.framework.input.devices;

import java.util.Set;


/**
 * Interface that collects the different types of inputs.
 */

public interface InputAdapter {

    /**
     * Implementations must return whether the adapter is enabled or not.
     *
     * @return true if the adapter is enabled, false otherwise.
     */
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
     * @param digitalInput the collection to add the currently pressed inputs.
     */
    void update(Set<String> digitalInput);
}
