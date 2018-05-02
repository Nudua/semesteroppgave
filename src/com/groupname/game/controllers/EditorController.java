package com.groupname.game.controllers;

import com.groupname.framework.audio.SoundPlayer;
import com.groupname.framework.concurrency.TaskRunner;
import com.groupname.framework.core.Difficulty;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.history.StackBasedUndoRedo;
import com.groupname.framework.history.UndoRedo;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.editor.LevelItem;
import com.groupname.game.editor.LevelReader;
import com.groupname.game.editor.LevelReaderException;
import com.groupname.game.editor.LevelWriter;
import com.groupname.game.entities.enemies.HomingEnemy;
import com.groupname.game.entities.enemies.TowerEnemy;
import com.groupname.game.scene.SceneManager;
import com.groupname.game.scene.SceneName;
import com.groupname.game.core.Game;
import com.groupname.game.core.GameEditor;
import com.groupname.game.editor.metadata.LevelMetaData;
import com.groupname.game.editor.controls.MetaDataListCell;
import com.groupname.game.editor.metadata.EnemyMetaData;
import com.groupname.game.editor.metadata.LevelFactory;
import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.EnemySpriteType;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.enemies.GuardEnemy;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static javafx.scene.control.Alert.AlertType;

public class EditorController implements Controller {

    @FXML protected GridPane root;
    @FXML protected Canvas canvas;
    @FXML protected ListView<ObjectMetaData> metaDataListView;

    @FXML protected MenuItem editMenuItem;
    @FXML protected MenuItem playMenuItem;

    private Game game;

    private GameEditor editor;
    private UndoRedo commandHistory;

    private List<LevelItem> levelItems;
    private LevelFactory levelFactory;

    private LevelItem selectedItem = null;

    private LevelMetaData levelMetaData;
    private final TaskRunner taskRunner;

    public EditorController() {
        taskRunner = new TaskRunner();
        commandHistory = new StackBasedUndoRedo();
        levelItems = new ArrayList<>();
    }

    private void setupBindings() {
        editMenuItem.disableProperty().bindBidirectional(editor.editDisabledProperty());
        playMenuItem.disableProperty().bindBidirectional(editor.playDisabledProperty());
    }

    @FXML
    public void initialize() {
        populateMetaDataList();
    }

    private void populateMetaDataList() {
        levelMetaData = new LevelMetaData("Default level");

        ObjectMetaData meta1 = new ObjectMetaData("Player", Player.class);
        EnemyMetaData meta2 = new EnemyMetaData("Guard Blob - Easy", GuardEnemy.class);
        EnemyMetaData meta3 = new EnemyMetaData("Guard Bee - Easy", GuardEnemy.class);
        EnemyMetaData meta4 = new EnemyMetaData("Crazy Bee - Medium", GuardEnemy.class);

        EnemyMetaData meta5 = new EnemyMetaData("Homing Snail - Easy", HomingEnemy.class);
        meta5.setSpriteType(EnemySpriteType.Snail);

        EnemyMetaData meta7 = new EnemyMetaData("Homing Snail - Medium", HomingEnemy.class);
        meta7.setSpriteType(EnemySpriteType.Snail);
        meta7.setDifficulty(Difficulty.Medium);

        EnemyMetaData meta8 = new EnemyMetaData("Homing Snail - hard", HomingEnemy.class);
        meta8.setSpriteType(EnemySpriteType.Snail);
        meta8.setDifficulty(Difficulty.Hard);


        EnemyMetaData meta6 = new EnemyMetaData("Tower - Easy", TowerEnemy.class);
        meta6.setSpriteType(EnemySpriteType.Squareboss);

        meta3.setSpriteType(EnemySpriteType.Bee);
        meta4.setSpriteType(EnemySpriteType.CrazyBee);
        meta4.setDifficulty(Difficulty.Medium);

        //ObjectMetaData meta2 = new ObjectMetaData("Second", ObjectMetaData.class, new Vector2D());
        //ObjectMetaData meta3 = new ObjectMetaData("Third", ObjectMetaData.class, new Vector2D());

        // Move to a css file
        metaDataListView.setStyle("-fx-control-inner-background-alt: -fx-control-inner-background;");
        metaDataListView.setItems(FXCollections.observableArrayList(meta1, meta2, meta3, meta4, meta5, meta6, meta7, meta8));


        metaDataListView.setCellFactory((o) -> new MetaDataListCell());

        metaDataListView.setOnMouseClicked(this::gameItemSelected);
    }

    private void gameItemSelected(MouseEvent event) {
        ObjectMetaData sourceMetaData = metaDataListView.getSelectionModel().getSelectedItem();

        if(sourceMetaData != null) {

            // Create a copy of the selected item
            ObjectMetaData metaData = sourceMetaData.deepCopy();

            Optional<LevelItem> playerItem = getPlayerIfExists();

            if(metaData.getType() == HomingEnemy.class || metaData.getType() == TowerEnemy.class) {
                if(playerItem.isPresent()) {
                    levelFactory.setPlayer((Player)playerItem.get().getInstance());
                } else {
                    showError("Error", "This enemy requires a player to be placed first...");
                    return;
                }
            }

            // Check instance of here
            GameObject gameObject = levelFactory.create(metaData);

            if(gameObject != null) {
                LevelItem newItem = new LevelItem(metaData, gameObject);

                if(gameObject instanceof Player) {
                    Optional<LevelItem> player = getPlayerIfExists();

                    selectedItem = player.orElse(newItem);

                } else {
                    selectedItem = newItem;
                }

                selectedItem.setPosition(new Vector2D(1280 / 2, 720 / 2));
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

        SoundPlayer.INSTANCE.playMusic(SoundPlayer.MusicTrack.Editor);

        setupBindings();

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
    protected void playOnClicked(ActionEvent event) {
        editor.setMode(GameEditor.Mode.Playing);
    }

    @FXML
    protected void editOnClicked(ActionEvent event) {
        //editor.setMode(GameEditor.Mode.Editing);
        editor.reset();
    }

    @FXML
    protected void newOnClicked(ActionEvent event) {
        // todo: add confirmation
        newLevel();
    }

    @FXML
    protected void openOnClicked(ActionEvent event) {
        newLevel();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save level");

        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile == null) {
            showError("Error", "No file was selected, aborting...");
            return;
        }

        LevelReader reader = new LevelReader();

        try {
            LevelMetaData level = reader.read(selectedFile.toPath());

            levelMetaData = level;

            for(ObjectMetaData object : level.getObjectMetaDataList()) {
                ObjectMetaData copy = object.deepCopy();
                GameObject gameObject = levelFactory.create(copy);


                LevelItem levelItem = new LevelItem(copy, gameObject);

                levelItem.setPosition(levelItem.getPosition());
                levelItem.setPlaced(true);

                if(gameObject instanceof Player) {
                    levelItem.setPlayer(true);
                }

                levelItems.add(levelItem);
            }

        } catch (LevelReaderException exception) {
            showError("Error", exception.getMessage());
            return;
        }

        showAlert("Success", "Level loaded successfully.", false);
    }

    @FXML
    protected void deleteOnClicked(ActionEvent event) {
        editor.deleteSelectedItem();
    }

    private void newLevel() {
        levelMetaData = new LevelMetaData("level1");
        levelItems.clear();
        selectedItem = null;
        editor.setSelectedItem(null);
    }



    @FXML
    protected void saveAsOnClicked(ActionEvent event) {
        // Split
        boolean isValid = validateLevel();

        if(!isValid) {
            showError("Unable to save!", "A level has to have at least one player and one enemy placed!");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save level");

        File selectedFile = fileChooser.showSaveDialog(null);

        if(selectedFile == null) {
            showError("Error", "No file was selected, aborting...");
            return;
        }

        // Split
        writeLevel(selectedFile);
    }

    private boolean validateLevel() {
        List<ObjectMetaData> objects = levelMetaData.getObjectMetaDataList();
        objects.clear();

        boolean hasPlayer = false;
        boolean hasEnemy = false;

        for(LevelItem levelItem : levelItems) {
            if(levelItem.getInstance() instanceof Player) {
                hasPlayer = true;
            } else if(levelItem.getInstance() instanceof Enemy) {
                hasEnemy = true;
            }

            if(levelItem.isPlaced()) {
                objects.add(levelItem.getMetaData());
            }
        }

        return hasPlayer && hasEnemy;
    }

    private void writeLevel(File selectedFile) {
        Supplier<Boolean> writeLevel = () -> {
            LevelWriter levelWriter = new LevelWriter();

            try {
                levelWriter.write(levelMetaData, selectedFile.toPath());
            } catch (IOException exception) {
                return false;
            }

            return true;
        };

        Consumer<Boolean> onCompleted = (success) -> {
            if (success) {
                showAlert("Success", "Saved the level successfully", false);
            } else {
                showAlert("Error", "Unable to save the level", true);
            }
        };

        // Do this work off thread with the taskrunner then invoke back on the javafx thread with the results when done
        taskRunner.submit(writeLevel, onCompleted);
    }

    private void showError(String title, String message) {
        showAlert(title, message, true);
    }

    private void showAlert(String title, String message, boolean isError) {
        AlertType alertType = isError ? AlertType.ERROR : AlertType.INFORMATION;

        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(title);
        alert.show();
    }

    @Override
    public void exit() {
        if(!taskRunner.isShutdown()){
            System.out.println("Shutting down the taskrunner thread");

            try {
                taskRunner.stop();
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @FXML
    protected void exitOnClicked(ActionEvent event) {
        SceneManager.navigate(SceneName.Title);
    }
}














