package com.groupname.game.controllers;

import com.groupname.framework.audio.SoundPlayer;
import com.groupname.framework.concurrency.TaskRunner;
import com.groupname.framework.core.Difficulty;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.history.StackBasedUndoRedo;
import com.groupname.framework.history.UndoRedo;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.framework.serialization.ObjectSerializer;
import com.groupname.framework.serialization.SerializationException;
import com.groupname.game.core.Game;
import com.groupname.game.core.GameEditor;
import com.groupname.game.data.AppSettings;
import com.groupname.game.editor.*;
import com.groupname.game.editor.controls.MetaDataListCell;
import com.groupname.game.editor.metadata.*;
import com.groupname.game.entities.*;
import com.groupname.game.entities.enemies.*;
import com.groupname.game.scene.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.groupname.framework.util.Alerts.*;

/**
 * This controller is used to connect the fxml (views/editorview.fxml)
 * and with the GameEditor that is used to create levels for this game.
 */
public class EditorController implements Controller {

    @FXML protected GridPane root;
    @FXML protected Canvas canvas;
    @FXML protected ListView<ObjectMetaData> metaDataListView;

    @FXML protected MenuItem editMenuItem;
    @FXML protected MenuItem playMenuItem;

    private Game game;

    private GameEditor editor;
    private final UndoRedo commandHistory;

    private final List<LevelItem> levelItems;
    private LevelObjectFactory levelObjectFactory;

    private LevelItem selectedItem = null;

    private LevelMetaData levelMetaData;
    private final TaskRunner taskRunner;

    /**
     * Creates a new instance of this controller and should be loaded with the fxml located in /views/editorview.fxml.
     */
    public EditorController() {
        taskRunner = new TaskRunner();
        commandHistory = new StackBasedUndoRedo();
        levelItems = new ArrayList<>();
    }

    private void setupBindings() {
        editMenuItem.disableProperty().bindBidirectional(editor.editDisabledProperty());
        playMenuItem.disableProperty().bindBidirectional(editor.playDisabledProperty());
    }

    // Gets called when the fxml has completed loading its nodes.
    @FXML
    protected void initialize() {
        //writeMetaData();
        loadMetadata();
    }

    // Loads all the metaData for the objects that are used within the game (enemies, powerups and player)
    private void loadMetadata() {
        try {
            List<ObjectMetaData> metaData = Content.loadMetadata("metadata.data");

            metaDataListView.setStyle("-fx-control-inner-background-alt: -fx-control-inner-background;");
            metaDataListView.setItems(FXCollections.observableArrayList(metaData));

            metaDataListView.setCellFactory((o) -> new MetaDataListCell());

            metaDataListView.setOnMouseClicked(this::gameItemSelected);

        } catch (SerializationException exception) {
            showError("Unable to load level items");
        }
    }

    // Remove
    private void writeMetaData() {
        List<ObjectMetaData> allData = new ArrayList<>();

        ObjectMetaData player = new ObjectMetaData("Player", Player.class);

        // Guard enemies
        EnemyMetaData guardBlobEasy = new EnemyMetaData("Guard Blob - Easy", GuardEnemy.class);
        guardBlobEasy.setSpriteType(EnemySpriteType.BLUE_BLOB);

        EnemyMetaData guardBeeEasy = new EnemyMetaData("Guard Bee - Easy", GuardEnemy.class);
        guardBeeEasy.setSpriteType(EnemySpriteType.BEE);

        EnemyMetaData guardBeeMedium = new EnemyMetaData("Crazy BEE - Medium", GuardEnemy.class);
        guardBeeMedium.setDifficulty(Difficulty.MEDIUM);
        guardBeeMedium.setSpriteType(EnemySpriteType.CRAZY_BEE);

        // Homing
        EnemyMetaData homingSnailEasy = new EnemyMetaData("Homing Snail - Easy", HomingEnemy.class);
        homingSnailEasy.setSpriteType(EnemySpriteType.SNAIL);

        EnemyMetaData homingSnailMedium = new EnemyMetaData("Homing Snail - Medium", HomingEnemy.class);
        homingSnailMedium.setSpriteType(EnemySpriteType.SNAIL);
        homingSnailMedium.setDifficulty(Difficulty.MEDIUM);

        EnemyMetaData homingSnailHard = new EnemyMetaData("Homing Snail - Hard", HomingEnemy.class);
        homingSnailHard.setSpriteType(EnemySpriteType.SNAIL);
        homingSnailHard.setDifficulty(Difficulty.HARD);

        // Tower
        EnemyMetaData towerEasy = new EnemyMetaData("Tower - Easy", TowerEnemy.class);
        towerEasy.setSpriteType(EnemySpriteType.JELLYFISH);

        // Boss
        EnemyMetaData boss = new EnemyMetaData("Boss", BossEnemy.class);
        boss.setSpriteType(EnemySpriteType.SQUAREBOSS);


        allData.addAll(Arrays.asList(player, guardBlobEasy, guardBeeEasy, guardBeeMedium, homingSnailEasy, homingSnailMedium, homingSnailHard, towerEasy, boss));

        ObjectSerializer serializer = new ObjectSerializer();
        try {
            serializer.write(allData, Paths.get("metadata.data"));
        } catch (SerializationException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void gameItemSelected(MouseEvent event) {
        ObjectMetaData sourceMetaData = metaDataListView.getSelectionModel().getSelectedItem();

        if(sourceMetaData != null) {

            // Create a copy of the selected item
            ObjectMetaData metaData = sourceMetaData.deepCopy();

            Optional<LevelItem> playerItem = getPlayerIfExists();

            if(metaData.getType() == HomingEnemy.class || metaData.getType() == TowerEnemy.class || metaData.getType() == BossEnemy.class) {
                if(playerItem.isPresent()) {
                    levelObjectFactory.setPlayer((Player)playerItem.get().getInstance());
                } else {
                    showError("This enemy requires a player to be placed first...");
                    return;
                }
            }

            // Check instance of here
            GameObject gameObject = levelObjectFactory.create(metaData);

            if(gameObject != null) {
                LevelItem newItem = new LevelItem(metaData, gameObject);

                if(gameObject instanceof Player) {
                    Optional<LevelItem> player = getPlayerIfExists();

                    selectedItem = player.orElse(newItem);

                } else {
                    selectedItem = newItem;
                }

                Size screenBounds = AppSettings.SCREEN_BOUNDS;

                selectedItem.setPosition(new Vector2D(screenBounds.getWidth() / 2, screenBounds.getHeight() / 2));
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

    /**
     * Initializes the controller with the specified game to run on the specified game.
     *
     * @param parameters optional parameters that you want to pass to the controller when initializing.
     */
    public void init(Game game, Object parameters) {
        this.game = Objects.requireNonNull(game);

        game.initialize(canvas, this::update, this::draw);

        editor = new GameEditor(game, canvas, levelItems, commandHistory);
        editor.initialize();

        levelObjectFactory = new LevelObjectFactory(game.getInputManager());

        SoundPlayer.INSTANCE.playMusic(SoundPlayer.MusicTrack.EDITOR);

        setupBindings();

        newLevel();

        if(!game.isRunning()) {
            game.start();
        }
    }

    // This method is used to update input and the editor logic 60 times per second
    private void update(InputManager inputManager) {
        inputManager.update();
        editor.update();
    }

    // This method is used to draw the editor
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
        editor.setMode(GameEditor.Mode.PLAYING);
    }

    @FXML
    protected void editOnClicked(ActionEvent event) {
        editor.reset();
    }

    @FXML
    protected void newOnClicked(ActionEvent event) {
        newLevel();
    }

    @FXML
    protected void openOnClicked(ActionEvent event) {
        newLevel();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save level");

        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile == null) {
            showError("No file was selected, aborting...");
            return;
        }

        ObjectSerializer reader = new ObjectSerializer();

        try {
            LevelMetaData level = reader.read(selectedFile.toPath(), LevelMetaData.class);

            levelMetaData = level;

            for(ObjectMetaData object : level.getObjectMetaDataList()) {
                ObjectMetaData copy = object.deepCopy();
                GameObject gameObject = levelObjectFactory.create(copy);


                LevelItem levelItem = new LevelItem(copy, gameObject);

                levelItem.setPosition(levelItem.getPosition());
                levelItem.setPlaced(true);

                if(gameObject instanceof Player) {
                    levelItem.setPlayer(true);
                }

                levelItems.add(levelItem);
            }

        } catch (SerializationException exception) {
            showError(exception.getMessage());
            return;
        }

        showAlert("Success", "Level loaded successfully.");
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
            showError("No file was selected, aborting...");
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
            ObjectSerializer serializer = new ObjectSerializer();

            try {
                serializer.write(levelMetaData, selectedFile.toPath());
            } catch (SerializationException exception) {
                return false;
            }

            return true;
        };

        Consumer<Boolean> onCompleted = (success) -> {
            if (success) {
                showAlert("Success", "Saved the level successfully");
            } else {
                showAlert("Error", "Unable to save the level");
            }
        };

        // Do this work off thread with the taskrunner then invoke back on the javafx thread with the results when done
        taskRunner.submit(writeLevel, onCompleted);
    }

    /**
     * Stops the current taskRunner used with this controller.
     *
     * Must be called when navigating away from this controller.
     */
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
        SceneManager.navigate(SceneName.TITLE);
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "EditorController{" +
                "root=" + root +
                ", canvas=" + canvas +
                ", metaDataListView=" + metaDataListView +
                ", editMenuItem=" + editMenuItem +
                ", playMenuItem=" + playMenuItem +
                ", game=" + game +
                ", editor=" + editor +
                ", commandHistory=" + commandHistory +
                ", levelItems=" + levelItems +
                ", levelObjectFactory=" + levelObjectFactory +
                ", selectedItem=" + selectedItem +
                ", levelMetaData=" + levelMetaData +
                ", taskRunner=" + taskRunner +
                '}';
    }
}














