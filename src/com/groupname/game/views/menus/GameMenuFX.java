package com.groupname.game.views.menus;

import com.groupname.framework.core.GameMenu;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.util.Strings;
import com.groupname.game.input.PlayerInputDefinitions;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


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
public class GameMenuFX<T extends Enum<T>> extends VBox implements GameMenu<T> {

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

    private HashMap<T, MenuItem> menuItems;

    public GameMenuFX(Class <T> enumType, String fxmlSource) {
        Strings.requireNonNullAndNotEmpty(fxmlSource);

        if(!enumType.isEnum()) {
            throw new InvalidParameterException();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(GameMenuFX.class.getResource(fxmlSource));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        menuItems = new HashMap<>();

        createButtons(enumType.getEnumConstants());

        Platform.runLater(() -> {
            focusButton(enumType.getEnumConstants()[0]);
        });

        visibleProperty().addListener((observable, oldValue, newValue) -> {
            // When the menu is shown, reset the currentIndex to the first item in the menu (usually resume)
            if(newValue) {
                currentIndex = 0;
            }
        });
    }

    public void focusButton(T button) {
        menuItems.get(button).button.requestFocus();
    }

    private void createButtons(T[] enumConstants) {
        int index = 0;
        for(T constant : enumConstants) {
            Button button = createButton(constant, index);
            menuItems.put(constant, new MenuItem(index, button));
            index++;
        }
    }

    private String addWhiteSpaceOnUppercaseLetter(String value) {
        String[] parts = value.split("(?<=.)(?=\\p{Lu})");

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < parts.length; i++) {
            sb.append(parts[i]);

            if(i < parts.length - 1) {
                sb.append(' ');
            }
        }

        return sb.toString();
    }

    private Button createButton(T enumConstant, final int index) {
        Button button = new Button(addWhiteSpaceOnUppercaseLetter(enumConstant.name()));
        button.setFocusTraversable(false);
        button.getStyleClass().add("titlebutton");
        button.setOnAction((event -> runActionIfExists(enumConstant)));
        button.setOnMouseEntered((event ->
        {
            currentIndex = index;
            button.requestFocus();
        }));

        this.getChildren().add(button);
        return button;
    }


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

    private MenuItem getMenuItemFromIndex(int index) {
        Optional<MenuItem> menuItem = menuItems.entrySet()
                .stream()
                .filter(item -> item.getValue().index == index)
                .map(Map.Entry::getValue)
                .findFirst();

        return menuItem.orElse(null);
        //return menuItem.isPresent() ? menuItem.get() : null;
    }

    private int currentIndex = 0;

    /**
     * Updates the current state of the GameMenu.
     */
    @Override
    public void update(InputManager inputManager) {
        Objects.requireNonNull(inputManager);

        boolean selectedMenuItemChanged = false;

        if(inputManager.wasPressed(PlayerInputDefinitions.UP) || inputManager.wasPressed(PlayerInputDefinitions.SHOOT_UP)) {
            currentIndex--;

            // Wrap around to the bottom of the menu
            if(currentIndex < 0) {
                currentIndex = menuItems.values().size() - 1;
            }

            selectedMenuItemChanged = true;

        } else if(inputManager.wasPressed(PlayerInputDefinitions.DOWN) || inputManager.wasPressed(PlayerInputDefinitions.SHOOT_DOWN)) {
            currentIndex++;

            // Wrap around to the top
            if(currentIndex > menuItems.values().size() - 1) {
                currentIndex = 0;
            }

            selectedMenuItemChanged = true;
        }

        if(selectedMenuItemChanged) {
            MenuItem current = getMenuItemFromIndex(currentIndex);
            if(current != null) {
                current.button.requestFocus();
            }
        }

        if(inputManager.wasPressed(PlayerInputDefinitions.START)) {
            MenuItem current = getMenuItemFromIndex(currentIndex);
            System.out.println("Executing: " + current.index);

            if(current.action != null) {
                current.action.run();
            }
        }
    }
}

