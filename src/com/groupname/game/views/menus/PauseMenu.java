package com.groupname.game.views.menus;

import com.groupname.framework.core.GameMenu;
import com.groupname.framework.input.InputManager;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Objects;

/**
 * Represents a menu that is shown when the game is paused.
 *
 * Implementations must support all the buttons in the GameMenu enumeration:
 *          *****************
 *          *     PAUSED    * <- Sample header
 *          *****************
 *          * Resume        * <- GameMenu.Resume
 *          * Save          * <- GameMenu.Save etc.
 *          * Restart Level *
 *          * Restart Game  *
 *          * Exit          *
 *          *****************
 *
 *  This implementation is based on FXML with the buttons and general layout is defined within the pausemenu.fxml file
 *  and the style is contained within pausemenu.css.
 */
public class PauseMenu<T extends Enum<T>> extends VBox implements GameMenu<T> {

    // Internal helper class
    private class MenuItem {
        private final int index;
        private final Button button;
        private Runnable action = null;

        private MenuItem(int index, Button button) {
            this.index = index;
            this.button = Objects.requireNonNull(button);
        }
    }

    private final HashMap<T, MenuItem> menuItems;
    private final InputManager inputManager;

    public PauseMenu(Class<T> enumType, InputManager inputManger) {
        this.inputManager = Objects.requireNonNull(inputManger);

        if(!enumType.isEnum()) {
            throw new InvalidParameterException();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(PauseMenu.class.getResource("/com/groupname/game/views/menus/pausemenu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        menuItems = new HashMap<>();

        createButtons(enumType.getEnumConstants());
    }

    private void createButtons(T[] enumConstants) {
        int index = 0;
        for(T constant : enumConstants) {
            Button button = createButton(constant);
            menuItems.put(constant, new MenuItem(index, button));
            index++;
        }
    }

    private Button createButton(T enumConstant) {
        Button button = new Button(enumConstant.name());
        this.getChildren().add(button);
        return button;
    }

    /*
    private void createAndSetupButtonsMap() {
        assert buttons != null;

        Button[] allButtons = {resumeButton, saveButton, restartLevelButton, restartGameButton, exitButton};
        PauseButton[] pauseButtons = PauseButton.values();

        assert allButtons.length == pauseButtons.length;

        for(int i = 0; i < allButtons.length; i++) {
            Button currentButton = allButtons[i];
            PauseButton currentPauseButton = pauseButtons[i];

            currentButton.setOnAction((event) -> runActionIfExists(currentPauseButton));

            buttons.put(pauseButtons[i], allButtons[i]);
        }
    }
    */

    /**
     * Sets the Runnable to be executed when the selected pausebutton is pressed by the user.
     *
     * @param button to assign the Runnable to.
     * @param action the Runnable to execute.
     */
    @Override
    public void setOnClicked(T button, Runnable action) {
        Objects.requireNonNull(button);
        Objects.requireNonNull(action);

        MenuItem item = menuItems.get(button);
        item.action = action;
    }

    /**
     * Sets the visibility of the selected PauseButton.
     * @param button the element to set the visibility of.
     * @param visibility the new state.
     */
    @Override
    public void setButtonVisibility(T button, boolean visibility) {
        Objects.requireNonNull(button);

        MenuItem item = menuItems.get(button);
        item.button.setVisible(visibility);
    }

    /**
     * Sets if the button is currently enabled or not.
     * If the button is disabled the attached runnable (if it exists) will not be executed.
     *
     * @param button the element to enable or disable.
     * @param enabled the new state.
     */
    @Override
    public void setButtonEnabled(T button, boolean enabled) {
        Objects.requireNonNull(button);

        MenuItem item = menuItems.get(button);
        item.button.setDisable(!enabled);
    }

    private void runActionIfExists(T button) {
        assert button != null;

        MenuItem item = menuItems.get(button);
        if(item.action != null) {
            item.action.run();
        }
    }

    /**
     * Updates the current state of the GameMenu.
     */
    @Override
    public void update() {
        // Sort out focusing here via the inputmanager

    }
}

