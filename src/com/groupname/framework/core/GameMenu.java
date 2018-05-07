package com.groupname.framework.core;

import com.groupname.framework.input.InputManager;

/**
 * Represents a dynamically generated menu for a game or application.
 *
 * Implementations must generate all the buttons specified by the Enum<T> supplied.
*/
public interface GameMenu<T extends Enum<T>> {

    /**
     * Sets the Runnable to be executed when the selected enum value (button) is pressed by the user.
     *
     * @param button to assign the Runnable to.
     * @param action the Runnable to execute.
     */
    void setOnClicked(T button, Runnable action);


    /**
     * Sets the visibility of the selected button.
     * @param button the element to set the visibility of.
     * @param visibility the new state.
     */
    void setButtonVisibility(T button, boolean visibility);

    /**
     * Sets if the button is currently enabled or not.
     * If the button is disabled the attached runnable (if it exists) will not be executed.
     *
     * @param button the element to enable or disable.
     * @param enabled the new state.
     */
    void setButtonEnabled(T button, boolean enabled);

    /**
     * Updates the current state of the GameMenu.
     * Should be called within your game loop.
     */
    void update(InputManager inputManager);
}

