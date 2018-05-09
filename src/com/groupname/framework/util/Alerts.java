package com.groupname.framework.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import static javafx.scene.control.Alert.AlertType;

/**
 * Static helper class for showing JavaFX alerts such as errors and informational popups.
 */
public final class Alerts {

    private static final String ERROR_TITLE = "Error";
    private static final String SHOW_TITLE = "Information";

    public static void showError(String message) {
        showError(ERROR_TITLE, message);
    }

    public static void showError(String title, String message) {
        displayAlert(title, message, AlertType.ERROR);
    }

    public static void showAlert(String message) {
        showAlert(SHOW_TITLE, message);
    }

    public static void showAlert(String title, String message) {
        displayAlert(title, message, AlertType.INFORMATION);
    }

    private static void displayAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(title);
        alert.show();
    }
}
