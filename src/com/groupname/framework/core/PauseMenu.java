package com.groupname.framework.core;

/**
 * Represents a menu that is shown when the game is paused.
 *
 * Implementations must support all the buttons in the PauseMenu enumeration:
 *          *****************
 *          *     PAUSED    * <- Sample header
 *          *****************
 *          * Resume        * <- PauseMenu.Resume
 *          * Save          * <- PauseMenu.Save etc.
 *          * Restart Level *
 *          * Restart Game  *
 *          * Exit          *
 *          *****************
 */
public interface PauseMenu {

    /**
     * Sets the Runnable to be executed when the selected pausebutton is pressed by the user.
     *
     * @param button to assign the Runnable to.
     * @param action the Runnable to execute.
     */
    void setOnClicked(PauseButton button, Runnable action);


    /**
     * Sets the visibility of the selected PauseButton.
     * @param button the element to set the visibility of.
     * @param visibility the new state.
     */
    void setButtonVisibility(PauseButton button, boolean visibility);

    /**
     * Sets if the button is currently enabled or not.
     * If the button is disabled the attached runnable (if it exists) will not be executed.
     *
     * @param button the element to enable or disable.
     * @param enabled the new state.
     */
    void setButtonEnabled(PauseButton button, boolean enabled);

    /**
     * Updates the current state of the PauseMenu.
     * Should be called within your game loop.
     */
    void update();
}

