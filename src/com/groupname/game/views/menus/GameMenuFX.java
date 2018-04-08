package com.groupname.game.views.menus;

import com.groupname.framework.core.GameMenu;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.util.Strings;
import com.groupname.game.input.PlayerInputDefinitions;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

// Bugs, add proper mouse support, set initial focus, set focus when mouse clicked, mouse hover colors, MainMenu -> Main Menu (split on case)

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

    public GameMenuFX(Class<T> enumType, String fxmlSource) {
        Strings.requireNonNullAndNotEmpty(fxmlSource);

        if(!enumType.isEnum()) {
            throw new InvalidParameterException();
        }

        //"/com/groupname/game/views/menus/pausemenu.fxml"
        FXMLLoader fxmlLoader = new FXMLLoader(GameMenuFX.class.getResource(fxmlSource));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        //setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        menuItems = new HashMap<>();

        // Ignore the default keyboard input
        //this.setOnKeyPressed(KeyEvent::consume);
        //this.setOnKeyReleased(KeyEvent::consume);

        createButtons(enumType.getEnumConstants());

        Platform.runLater(() -> {
            focusButton(enumType.getEnumConstants()[0]);
        });
    }

    public void focusButton(T button) {
        menuItems.get(button).button.requestFocus();
    }

    private void createButtons(T[] enumConstants) {
        int index = 0;
        for(T constant : enumConstants) {
            Button button = createButton(constant);
            //button.setOnAction((event) -> runActionIfExists(constant));
            menuItems.put(constant, new MenuItem(index, button));
            index++;
        }
    }

    public String addWhiteSpaceOnUppercaseLetter(String value) {
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

    private Button createButton(T enumConstant) {
        Button button = new Button(addWhiteSpaceOnUppercaseLetter(enumConstant.name()));
        //button.setPrefSize(400,100);
        button.setFocusTraversable(false);
        button.getStyleClass().add("titlebutton");
        button.setOnAction((event -> runActionIfExists(enumConstant)));
        button.setOnMouseEntered((event -> button.requestFocus()));

        this.getChildren().add(button);
        return button;
    }


    private String parseButtonName(T enumConstant) {
        String name = enumConstant.name();

        if(name.length() == 1) {
            return name;
        }

        CharSequence letters = name;

        int index = indexOfUppercaseLetter(letters, 1);

        if(index == -1) {
            return name;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name.substring(1, index));

        return name;
    }

    private int indexOfUppercaseLetter(CharSequence letters, int startIndex) {
        assert startIndex < letters.length();

        for(int i = startIndex; i < letters.length(); i++) {
            if(Character.isUpperCase(letters.charAt(i))) {
                return i;
            }
        }
        return -1;
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

        return menuItem.isPresent() ? menuItem.get() : null;
    }

    int currentIndex = 0;

    /**
     * Updates the current state of the GameMenu.
     */
    @Override
    public void update(InputManager inputManager) {
        // Sort out focusing here via the inputmanager
        if(inputManager.wasPressed(PlayerInputDefinitions.UP) || inputManager.wasPressed(PlayerInputDefinitions.SHOOT_UP)) {

            currentIndex--;

            if(currentIndex < 0) {
                currentIndex = 0;
            } else {
                MenuItem current = getMenuItemFromIndex(currentIndex);
                if(current != null) {
                    current.button.requestFocus();
                }
            }
        } else if(inputManager.wasPressed(PlayerInputDefinitions.DOWN) || inputManager.wasPressed(PlayerInputDefinitions.SHOOT_DOWN)) {
            currentIndex++;

            if(currentIndex > menuItems.values().size() - 1) {
                currentIndex = menuItems.values().size() - 1;
            } else {
                MenuItem current = getMenuItemFromIndex(currentIndex);
                if(current != null) {
                    current.button.requestFocus();
                }
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

    private void setActiveMenuItem(MenuItem menuItem) {
        menuItem.button.requestFocus();
    }
}

