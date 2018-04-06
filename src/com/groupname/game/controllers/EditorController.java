package com.groupname.game.controllers;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.core.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

/*
interface Controller {
    void init(Game game);
}
*/

/*
class EditorItem {
    private final Vector2D position;
    private final Class<?> member;

    public EditorItem(Vector2D position, Class<?> member) {
        this.position = position;
        this.member = member;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D newPosition) {
        position.set(newPosition);
    }

    public Class<?> getMember() {
        return member;
    }
}
*/

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
