package com.groupname.game.core;

import com.groupname.framework.math.Size;
import com.groupname.framework.core.GameEngine;
import com.groupname.framework.input.InputManager;
import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.levels.*;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.levels.core.LevelState;
import javafx.scene.layout.Pane;

import java.util.*;

public class Game extends GameEngine {

    //private final SpriteBatch spritebatch;
    private final InputManager inputManager;

    // Might consider making a levelManager
    private List<LevelBase> allLevels;
    private LevelBase currentLevel;
    private int currentLevelIndex = 0;

    public Game(Pane parent, int width, int height) {
        super(parent, width, height);

        inputManager = new InputManager(scene);
        allLevels = new ArrayList<>();

        createLevels();
    }

    private void createLevels() {
        LevelBase level1 = new Level1(this, inputManager);
        LevelBase level2 = new Level2(this, inputManager);
        LevelBase level3 = new Level3(this, inputManager);
        LevelBase level4 = new Level4(this, inputManager);

        // Save initialize for the loading? Or maybe just load all the levels at start...
        level1.initialize();
        level2.initialize();
        level3.initialize();
        level4.initialize();

        allLevels.add(level1);
        allLevels.add(level2);
        allLevels.add(level3);
        allLevels.add(level4);

        currentLevel = level1;
    }

    protected void update() {

        if(isPaused()) {
            // Fade the current level and show the MainMenu
            return;
        }

        if(currentLevel.getState() == LevelState.Completed) {
            // Change to the next level
            currentLevelIndex++;

            // Just wrap around for now
            if(currentLevelIndex > allLevels.size() - 1) {
                currentLevelIndex = 0;
            }

            // cleanup / remove the old level here
            currentLevel = allLevels.get(currentLevelIndex);

            currentLevel.reset();
        }

        currentLevel.update();
    }

    private void changeScene(SceneName newScene) {
        // Stop the current Scene
        stop();

        SceneManager sceneManager = SceneManager.INSTANCE;
        sceneManager.changeToScene(newScene);
    }

    protected void draw() {
        if(isPaused()) {
            // Just return
            return;
        }

        // Otherwise we're playing
        currentLevel.draw();
    }
}
