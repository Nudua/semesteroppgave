package com.groupname.game.views.menus;

import com.groupname.framework.util.EmptyStringException;
import org.junit.Test;

public class GameMenuFXTests {

    private static final String VALID_FXML_PATH = "/com/groupname/game/views/menus/pausemenu.fxml";

    @Test(expected = NullPointerException.class)
    public void constructorEnumTypeCannotBeNull() {
        new GameMenuFX<PauseButton>(null, VALID_FXML_PATH);
    }

    @Test(expected = NullPointerException.class)
    public void constructorFXMLPathCannotBeNull() {
        new GameMenuFX<>(PauseButton.class, null);
    }

    @Test(expected = EmptyStringException.class)
    public void constructorFXMLPathCannotBeEmpty() {
        new GameMenuFX<>(PauseButton.class, "");
    }

    @Test(expected = NullPointerException.class)
    public void focusButtonEnumTypeCannotBeNull() {
        GameMenuFX<PauseButton> menu = new GameMenuFX<PauseButton>(PauseButton.class, VALID_FXML_PATH);

        menu.focusButton(null);
    }

    @Test(expected = NullPointerException.class)
    public void setOnClickedEnumTypeCannotBeNull() {
        GameMenuFX<PauseButton> menu = new GameMenuFX<PauseButton>(PauseButton.class, VALID_FXML_PATH);

        menu.setOnClicked(null, () -> {});
    }

    @Test(expected = NullPointerException.class)
    public void setOnClickedActionCannotBeNull() {
        GameMenuFX<PauseButton> menu = new GameMenuFX<>(PauseButton.class, VALID_FXML_PATH);

        menu.setOnClicked(PauseButton.Resume, null);
    }

    @Test(expected = NullPointerException.class)
    public void setButtonVisibilityEnumTypeCannotBeNull() {
        GameMenuFX<PauseButton> menu = new GameMenuFX<>(PauseButton.class, VALID_FXML_PATH);

        menu.setButtonVisibility(null, true);
    }

    @Test(expected = NullPointerException.class)
    public void setButtonEnabledEnumTypeCannotBeNull() {
        GameMenuFX<PauseButton> menu = new GameMenuFX<>(PauseButton.class, VALID_FXML_PATH);

        menu.setButtonEnabled(null, true);
    }

    @Test(expected = NullPointerException.class)
    public void updateInputManagerCannotBeNull() {
        GameMenuFX<PauseButton> menu = new GameMenuFX<>(PauseButton.class, VALID_FXML_PATH);

        menu.update(null);
    }

}
