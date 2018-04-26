package com.groupname.game.controllers;

import com.groupname.framework.input.InputManager;
import com.groupname.game.scene.SceneManager;
import com.groupname.game.scene.SceneName;
import com.groupname.game.core.Game;
import com.groupname.game.levels.Title;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.views.menus.GameMenuFX;
import com.groupname.game.views.menus.TitleMenuNames;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class TitleController implements Controller {

    @FXML protected GridPane root;
    @FXML protected Canvas canvas;

    private Game game;

    private GameMenuFX<TitleMenuNames> titleMenu;
    private LevelBase titleLevel;

    public TitleController() {

    }

    // Maybe move into constructor instead
    public void init(Game game) {
        this.game = Objects.requireNonNull(game);

        game.initialize(canvas, this::update, this::draw);

        titleLevel = new Title(game, canvas.getGraphicsContext2D());
        titleLevel.initialize();

        if(!game.isRunning()) {
            game.start();
        }

        setupMenu();
    }

    private void setupMenu() {
        titleMenu = new GameMenuFX<>(TitleMenuNames.class, "/com/groupname/game/views/menus/titlemenu.fxml");

        titleMenu.setOnClicked(TitleMenuNames.Exit, Platform::exit);
        titleMenu.setOnClicked(TitleMenuNames.Start, () -> SceneManager.navigate(SceneName.Game));
        titleMenu.setOnClicked(TitleMenuNames.Editor, () -> SceneManager.navigate(SceneName.Editor));
        //titleMenu.setOnClicked(TitleMenuNames.Continue, () -> titleMenu.setVisible(false));

        root.getChildren().add(titleMenu);
    }

    private void update(InputManager inputManager) {
        inputManager.update();

        titleMenu.update(inputManager);

        /*
        if(titleMenu.isVisible()) {
            titleMenu.update(inputManager);
        } else {
            if(inputManager.wasPressed(PlayerInputDefinitions.START)) {
                // Make titleMenu.show / hide
                titleMenu.setVisible(true);
            }
        }
        */

        titleLevel.update();
    }

    private void draw() {
        titleLevel.draw();
    }


    /*
    @FXML
    protected void navigateToEditorOnClick(ActionEvent event) {
        SceneManager sceneManager = SceneManager.INSTANCE;
        sceneManager.changeToScene(SceneName.Editor);
    }

    @FXML
    protected void navigateToGameOnClick(ActionEvent event) {
        SceneManager sceneManager = SceneManager.INSTANCE;
        sceneManager.changeToScene(SceneName.Game);
    }


    @FXML
    protected void exitGameOnClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    protected void navigateToCreditsOnClick(ActionEvent event) {
        SceneManager sceneManager = SceneManager.INSTANCE;
        sceneManager.changeToScene(SceneName.Credits);
    }
    */

    @Override
    public void exit() {

    }
}
