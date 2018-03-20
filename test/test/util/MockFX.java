package test.util;

import javafx.application.Application;
import javafx.stage.Stage;

public class MockFX extends Application {

    private static boolean initialized;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // do nothing
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
