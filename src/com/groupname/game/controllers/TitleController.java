package com.groupname.game.controllers;

import com.groupname.framework.audio.SoundPlayer;
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

/**
 * Controls the title menu.
 */
public class TitleController implements Controller {

    @FXML protected GridPane root;
    @FXML protected Canvas canvas;

    private Game game;

    private GameMenuFX<TitleMenuNames> titleMenu;
    private LevelBase titleLevel;

    /**
     * Creates a new instance of this class.
     */
    public TitleController() {
    }

    /**
     * Initializes the controller with the specified game to run on the specified game.
     *
      * @param game the game instance to use for this controller.
     */
    public void init(Game game) {
        this.game = Objects.requireNonNull(game);

        game.initialize(canvas, this::update, this::draw);

        titleLevel = new Title(game, canvas.getGraphicsContext2D());
        titleLevel.initialize();

        SoundPlayer.INSTANCE.playMusic(SoundPlayer.MusicTrack.MAIN);

        if(!game.isRunning()) {
            game.start();
        }

        setupMenu();
    }

    private void setupMenu() {
        titleMenu = new GameMenuFX<>(TitleMenuNames.class, "/com/groupname/game/views/menus/titlemenu.fxml");

        titleMenu.setOnClicked(TitleMenuNames.Exit, Platform::exit);
        titleMenu.setOnClicked(TitleMenuNames.Start, () -> SceneManager.navigate(SceneName.GAME));
        titleMenu.setOnClicked(TitleMenuNames.Editor, () -> SceneManager.navigate(SceneName.EDITOR));
        //titleMenu.setOnClicked(TitleMenuNames.Continue, () -> titleMenu.setVisible(false));

        root.getChildren().add(titleMenu);
    }

    private void update(InputManager inputManager) {
        inputManager.update();

        titleMenu.update(inputManager);
        titleLevel.update();
    }

    private void draw() {
        titleLevel.draw();
    }

    /**
     * Exit method.
     */
    @Override
    public void exit() {

    }
}
