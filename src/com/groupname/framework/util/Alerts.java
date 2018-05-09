package com.groupname.framework.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Objects;

import static javafx.scene.control.Alert.AlertType;

/**
 * Static helper class for showing JavaFX alerts such as errors and informational popups.
 */
public final class Alerts {

    private static final String ERROR_TITLE = "Error";
    private static final String SHOW_TITLE = "Information";

    /**
     * Shows an error alert dialog with the title "Error" and the specified message.
     *
     * @param message the message to show with this error alert.
     */
    public static void showError(String message) {
        showError(ERROR_TITLE, message);
    }

    /**
     * Shows an error alert dialog box with the specified title and message.
     *
     * @param title the title to show for this alert.
     * @param message the message to show with this error alert.
     */
    public static void showError(String title, String message) {
        displayAlert(title, message, AlertType.ERROR);
    }

    /**
     * Shows an alert with the specified message.
     *
     * @param message the message to display.
     */
    public static void showAlert(String message) {
        showAlert(SHOW_TITLE, message);
    }

    /**
     * Shows an alert with the specified title and message.
     *
     * @param title the title to show for this alert.
     * @param message the message to display.
     */
    public static void showAlert(String title, String message) {
        displayAlert(title, message, AlertType.INFORMATION);
    }

    private static void displayAlert(String title, String message, AlertType alertType) {
        assert !Strings.isNullOrEmpty(title);
        assert message != null;
        assert alertType != null;

        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(title);
        alert.show();
    }
}
