package com.groupname.game.controllers;

import com.groupname.framework.input.InputManager;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.core.Game;
import com.groupname.game.core.GameEditor;
import com.groupname.game.editor.MetaDataListCell;
import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.levels.core.LevelBase;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class EditorController implements Controller {

    @FXML protected GridPane root;
    @FXML protected Canvas canvas;
    @FXML protected ListView<ObjectMetaData> metaDataListView;

    private Game game;

    private LevelBase editor;
    

    @FXML
    public void initialize() {
        populateMetaDataList();
    }

    private void populateMetaDataList() {
        ObjectMetaData meta1 = new ObjectMetaData("First", ObjectMetaData.class, new Vector2D());
        ObjectMetaData meta2 = new ObjectMetaData("Second", ObjectMetaData.class, new Vector2D());
        ObjectMetaData meta3 = new ObjectMetaData("Third", ObjectMetaData.class, new Vector2D());

        // Move to a css file
        metaDataListView.setStyle("-fx-control-inner-background-alt: -fx-control-inner-background;");
        metaDataListView.setItems(FXCollections.observableArrayList(meta1, meta2, meta3));


        metaDataListView.setCellFactory((o) -> { return new MetaDataListCell(); });

        metaDataListView.setOnMouseClicked(this::gameItemSelected);
    }

    private void gameItemSelected(MouseEvent event) {

        ObjectMetaData metaData = metaDataListView.getSelectionModel().getSelectedItem();

        if(metaData != null) {
            System.out.println(metaDataListView.getSelectionModel().getSelectedItem().getName());
        }
    }

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
        editor.update();
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
