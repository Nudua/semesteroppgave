package com.groupname.game.controllers;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.core.PauseButton;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.core.Game;
import com.groupname.game.core.GameEditor;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.levels.Level1;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.views.menus.GameMenuFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class EditorController implements Controller {
    @FXML protected GridPane root;
    @FXML protected Canvas canvas;

    private Game game;

    private LevelBase editor;

    // Maybe move into constructor instead
    public void init(Game game) {
        this.game = Objects.requireNonNull(game);

        game.initialize(canvas, this::update, this::draw);

        editor = new GameEditor(game, canvas);
        editor.initialize();

        if(!game.isRunning()) {
            game.start();
        }
    }


    private void update(InputManager inputManager) {
        inputManager.update();
    }


    private void draw() {
        editor.draw();
    }

    @FXML
    protected void exitOnClicked(ActionEvent event) {
        SceneManager.navigate(SceneName.Title);
    }
}


/*
public class EditorController {

    private boolean initialized;
    private GameEngine game;

    private boolean playerSelected = false;

    public EditorController() {

    }

    public void init(GameEngine game) {
        this.game = Objects.requireNonNull(game);
        initialized = true;
    }


    @FXML
    protected void onPlayerSelected(MouseEvent event) {
        System.out.println("Player selected");
        playerSelected = !playerSelected;
    }

}
*/
