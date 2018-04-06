package com.groupname.game.views.menus;

import com.groupname.framework.core.PauseButton;
import com.groupname.framework.core.PauseMenu;
import com.groupname.framework.input.InputManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

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
 *
 *  This implementation is based on FXML with the buttons and general layout is defined within the pausemenu.fxml file
 *  and the style is contained within pausemenu.css.
 */
public class PauseMenuFX extends VBox implements PauseMenu {

    @FXML private Button resumeButton;
    @FXML private Button saveButton;
    @FXML private Button restartLevelButton;
    @FXML private Button restartGameButton;
    @FXML private Button exitButton;

    // Combine Runnable and button into one class?
    private final HashMap<PauseButton, Runnable> actions;
    private final HashMap<PauseButton, Button> buttons;

    private final InputManager inputManager;

    public PauseMenuFX(InputManager inputManger) {
        this.inputManager = Objects.requireNonNull(inputManger);

        FXMLLoader fxmlLoader = new FXMLLoader(PauseMenuFX.class.getResource("/com/groupname/game/views/menus/pausemenu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        actions = new HashMap<>();
        buttons = new HashMap<>();

        createAndSetupButtonsMap();
    }

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

    /**
     * Sets the Runnable to be executed when the selected pausebutton is pressed by the user.
     *
     * @param button to assign the Runnable to.
     * @param action the Runnable to execute.
     */
    @Override
    public void setOnClicked(PauseButton button, Runnable action) {
        Objects.requireNonNull(button);
        Objects.requireNonNull(action);

        actions.put(button, action);
    }

    /**
     * Sets the visibility of the selected PauseButton.
     * @param button the element to set the visibility of.
     * @param visibility the new state.
     */
    @Override
    public void setButtonVisibility(PauseButton button, boolean visibility) {
        Objects.requireNonNull(button);

        if(buttons.containsKey(button)) {
            Button pauseButton = buttons.get(button);
            pauseButton.setVisible(visibility);
        }
    }

    /**
     * Sets if the button is currently enabled or not.
     * If the button is disabled the attached runnable (if it exists) will not be executed.
     *
     * @param button the element to enable or disable.
     * @param enabled the new state.
     */
    @Override
    public void setButtonEnabled(PauseButton button, boolean enabled) {
        Objects.requireNonNull(button);

        if(buttons.containsKey(button)) {
            Button pauseButton = buttons.get(button);
            pauseButton.setDisable(!enabled);
        }
    }

    private void runActionIfExists(PauseButton button) {
        assert button != null;

        if(actions.containsKey(button)) {
            Runnable buttonAction = actions.get(button);
            assert  buttonAction != null;

            buttonAction.run();
        }
    }

    /**
     * Updates the current state of the PauseMenu.
     */
    @Override
    public void update() {
        // Sort out focusing here via the inputmanager
        assert inputManager != null;
    }

    /*
    @FXML
    protected void resumeOnClicked() {
        runActionIfExists(PauseButton.Resume);
    }

    @FXML
    protected void saveOnClicked() {
        runActionIfExists(PauseButton.Save);
    }

    @FXML
    protected void restartLevelOnClicked() {
        runActionIfExists(PauseButton.RestartLevel);
    }

    @FXML
    protected void restartGameOnClicked() {
        runActionIfExists(PauseButton.RestartGame);
    }

    @FXML
    protected void exitOnClicked() {
        // Save data first, then exit when done
        runActionIfExists(PauseButton.Resume);
        Platform.exit();
    }
    */
}

