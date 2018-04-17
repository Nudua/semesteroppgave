package com.groupname.game.controllers;

import com.groupname.framework.core.Difficulty;
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
import com.groupname.game.core.LevelMetaData;
import com.groupname.game.editor.MetaDataListCell;
import com.groupname.game.editor.metadata.EnemyMetaData;
import com.groupname.game.editor.metadata.LevelFactory;
import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.EnemySpriteType;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.levels.core.LevelBase;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static javafx.scene.control.Alert.AlertType;

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

    private LevelMetaData levelMetaData;

    public EditorController() {
        commandHistory = new StackBasedUndoRedo();
        levelItems = new ArrayList<>();
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
        meta3.setSpriteType(EnemySpriteType.Bee);
        meta4.setSpriteType(EnemySpriteType.CrazyBee);
        meta4.setDifficulty(Difficulty.Medium);

        //ObjectMetaData meta2 = new ObjectMetaData("Second", ObjectMetaData.class, new Vector2D());
        //ObjectMetaData meta3 = new ObjectMetaData("Third", ObjectMetaData.class, new Vector2D());

        // Move to a css file
        metaDataListView.setStyle("-fx-control-inner-background-alt: -fx-control-inner-background;");
        metaDataListView.setItems(FXCollections.observableArrayList(meta1, meta2, meta3, meta4));


        metaDataListView.setCellFactory((o) -> { return new MetaDataListCell(); });

        metaDataListView.setOnMouseClicked(this::gameItemSelected);
    }

    private void gameItemSelected(MouseEvent event) {
        ObjectMetaData sourceMetaData = metaDataListView.getSelectionModel().getSelectedItem();

        if(sourceMetaData != null) {

            // Create a copy of the selected item
            ObjectMetaData metaData = sourceMetaData.deepCopy();
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

        // todo: make own exception instead
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

        } catch (NoSuchFileException exception) {
            showError("Error", "File not found!");
            return;
        } catch (IOException exception) {
            showError("Error", "Error while reading the file");
            return;
        } catch (ClassNotFoundException exception) {
            showError("Error", "Unable to");
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

        LevelWriterService service = new LevelWriterService();

        service.setLevelMetaData(levelMetaData);
        service.setFilePath(selectedFile.toPath());

        service.setOnSucceeded((workerStateEvent) -> {
            showAlert("Success", "Your level was stored successfully.", false);
        });

        service.setOnFailed((workerStateEvent) -> {
            showError("Error", "An error occurred while trying to write the file to the disk.");
        });

        service.start();

        /*
        assert selectedFile != null;
        assert selectedFile.canWrite();

        final Task<Boolean> levelWriterTask = new Task<Boolean>() {
            @Override
            protected Boolean call() {
                LevelWriter levelWriter = new LevelWriter();

                try {
                    levelWriter.write(levelMetaData, selectedFile.toPath());
                } catch (IOException exception) {
                    return false;
                }

                return true;
            }
        };

        levelWriterTask.setOnSucceeded((workerStateEvent) -> {
            showAlert("Success", "Your level was stored successfully.", false);
        });

        levelWriterTask.setOnFailed((workerStateEvent) -> {
            showError("Error", "An error occurred while trying to write the file to the disk.");
        });

        levelWriterTask.run();
        */
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

    @FXML
    protected void exitOnClicked(ActionEvent event) {
        SceneManager.navigate(SceneName.Title);
    }
}

class LevelWriterService extends Service<Boolean> {

    private LevelMetaData levelMetaData;
    private Path filePath;

    public void setLevelMetaData(LevelMetaData levelMetaData) {
        this.levelMetaData = Objects.requireNonNull(levelMetaData);
    }

    public void setFilePath(Path filePath) {
        this.filePath = Objects.requireNonNull(filePath);
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() {
                LevelWriter levelWriter = new LevelWriter();

                try {
                    levelWriter.write(levelMetaData, filePath);
                } catch (IOException exception) {
                    return false;
                }

                return true;
            }
        };
    }
}














