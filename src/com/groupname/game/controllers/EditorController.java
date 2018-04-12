package com.groupname.game.controllers;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.history.StackBasedUndoRedo;
import com.groupname.framework.history.UndoRedo;
import com.groupname.framework.history.commands.ListAddCommand;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.core.Game;
import com.groupname.game.core.GameEditor;
import com.groupname.game.editor.MetaDataListCell;
import com.groupname.game.editor.metadata.EnemyMetaData;
import com.groupname.game.editor.metadata.LevelFactory;
import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.levels.core.LevelBase;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EditorController implements Controller {

    @FXML protected GridPane root;
    @FXML protected Canvas canvas;
    @FXML protected ListView<ObjectMetaData> metaDataListView;

    private Game game;

    private GameEditor editor;
    private UndoRedo commandHistory;

    private List<LevelItem> levelItems;
    private LevelFactory levelFactory;

    private LevelItem selectedItem = null;

    public EditorController() {
        commandHistory = new StackBasedUndoRedo();
        levelItems = new ArrayList<>();
    }


    @FXML
    public void initialize() {
        populateMetaDataList();
    }

    private void populateMetaDataList() {
        ObjectMetaData meta1 = new ObjectMetaData("Player", Player.class, new Vector2D());
        EnemyMetaData meta2 = new EnemyMetaData("Guard - Easy", GuardEnemy.class, new Vector2D());
        //ObjectMetaData meta2 = new ObjectMetaData("Second", ObjectMetaData.class, new Vector2D());
        //ObjectMetaData meta3 = new ObjectMetaData("Third", ObjectMetaData.class, new Vector2D());

        // Move to a css file
        metaDataListView.setStyle("-fx-control-inner-background-alt: -fx-control-inner-background;");
        metaDataListView.setItems(FXCollections.observableArrayList(meta1, meta2));


        metaDataListView.setCellFactory((o) -> { return new MetaDataListCell(); });

        metaDataListView.setOnMouseClicked(this::gameItemSelected);
    }

    private void gameItemSelected(MouseEvent event) {

        ObjectMetaData metaData = metaDataListView.getSelectionModel().getSelectedItem();

        if(metaData != null) {

            GameObject gameObject = levelFactory.create(metaData);

            if(gameObject != null) {
                LevelItem newItem = new LevelItem(metaData, gameObject);

                if(gameObject instanceof Player) {
                    Optional<LevelItem> player = getPlayerIfExists();

                    selectedItem = player.orElse(newItem);

                } else {
                    selectedItem = newItem;
                }

                selectedItem.setPlaced(false);

                editor.setSelectedItem(selectedItem);

                System.out.println("SelectedItem set to: " + metaData.getName());
            }
        }
    }

    private Optional<LevelItem> getPlayerIfExists() {
        return levelItems.stream()
                .filter(n -> n.getInstance() != null && n.getInstance() instanceof Player)
                .findFirst();
    }

    // Maybe move into constructor instead
    public void init(Game game) {
        this.game = Objects.requireNonNull(game);

        game.initialize(canvas, this::update, this::draw);

        editor = new GameEditor(game, canvas, levelItems, commandHistory);
        editor.initialize();

        levelFactory = new LevelFactory(game.getInputManager());
        levelFactory.initialize();

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

    // FXML Buttons

    @FXML
    protected void undoOnClicked(ActionEvent event) {
        if(commandHistory.canUndo()) {
            commandHistory.undo();
        }
    }

    @FXML
    protected void redoOnClicked(ActionEvent event) {
        if(commandHistory.canRedo()) {
            commandHistory.redo();
        }
    }

    @FXML
    protected void exitOnClicked(ActionEvent event) {
        SceneManager.navigate(SceneName.Title);
    }
}
