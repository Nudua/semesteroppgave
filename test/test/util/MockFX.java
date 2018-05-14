package test.util;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Helper class for test components that require a JavaFX thread to be running.
 */
public class MockFX extends Application {

    private static Stage stage;
    private static boolean initialized;

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
    }

    public static void initFX() {
        if(initialized) {
            return;
        }

        Thread fxThread = new Thread(() -> Application.launch(MockFX.class));
        fxThread.setDaemon(true);
        fxThread.start();

        initialized = true;
    }
}
