package com.groupname.game;

import com.groupname.game.controllers.MainWindowController;
import com.groupname.game.core.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/mainwindow.fxml"));

        GridPane root = loader.load();
        Scene scene = new Scene(root);

        Game game = new Game(root, scene, 640, 480);
        game.start();

        MainWindowController mainController = loader.getController();
        mainController.init(game);

        primaryStage.setScene(scene);

        primaryStage.setTitle("Project X - Semesteroppgave!");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
