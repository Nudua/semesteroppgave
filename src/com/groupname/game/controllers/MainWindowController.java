package com.groupname.game.controllers;

import com.groupname.game.core.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Objects;

public class MainWindowController {

    private Game game;

    public void init(Game game) {
        this.game = Objects.requireNonNull(game);
    }

    @FXML
    protected void menuExitOnClicked(ActionEvent event) {
        Platform.exit();
    }
}
