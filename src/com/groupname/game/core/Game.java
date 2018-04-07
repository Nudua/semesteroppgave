package com.groupname.game.core;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameMenu;
import com.groupname.framework.core.PauseButton;
import com.groupname.framework.core.UpdateAble;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteBatchFX;
import com.groupname.framework.input.InputManager;
import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.levels.*;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.levels.core.LevelState;
import com.groupname.game.other.SierpinksiTestLevel;
import com.groupname.game.views.menus.GameMenuFX;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

import java.util.*;
import java.util.function.Consumer;

enum View {
    Title,
    Game,
    Editor
}


public class Game extends GameEngine {
    private Consumer<InputManager> onUpdate;
    private Runnable onDraw;

    private InputManager inputManager;

    private boolean initialized = false;

    public Game(Scene owner, int width, int height) {
        super(width, height);

        //spriteBatch = new SpriteBatchFX(getGraphicsContext());
        inputManager = new InputManager(owner);
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = Objects.requireNonNull(inputManager);
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public void initialize(Canvas canvas, Consumer<InputManager> onUpdate, Runnable onDraw) {
        this.canvas = Objects.requireNonNull(canvas);
        // These are optional
        this.onUpdate = onUpdate;
        this.onDraw = onDraw;

        initialized = true;
    }

    @Override
    protected void update() {
        if(!initialized) {
            return;
        }

        if(onUpdate != null) {
            onUpdate.accept(inputManager);
        }
    }

    @Override
    protected void draw() {
        if(!initialized) {
            return;
        }

        if(onDraw != null) {
            onDraw.run();
        }
    }
}

/*

public class Game extends GameEngine {

    //private final SpriteBatch spritebatch;
    private final InputManager inputManager;

    // Might consider making a levelManager
    private List<LevelBase> allLevels;
    private LevelBase currentLevel;
    private int currentLevelIndex = 0;

    private LevelBase gameOver;

    private View currentView = View.Title;

    private LevelBase title;

    private UpdateAble menu;

    public void setMenu(UpdateAble menu) {
        this.menu = menu;
    }

    public Game(Scene owner, Canvas canvas, int width, int height) {
        super(width, height);

        setCanvas(canvas);
        // Might have to change the scene for the inputmanager, if not required then don't
        inputManager = new InputManager(owner);
        allLevels = new ArrayList<>();

        initialize();
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    private void initialize() {
        createLevels();
    }

    private void createLevels() {
        LevelBase level1 = new Level1(this, inputManager);
        LevelBase credits = new Credits(this, inputManager);
        gameOver = new GameOver(this, inputManager);

        // Save initialize for the loading? Or maybe just load all the levels at start...
        credits.initialize();
        gameOver.initialize();
        level1.initialize();

        allLevels.add(level1);
        allLevels.add(credits);

        currentLevel = level1;

        title = new Title(this, inputManager);
        title.initialize();
    }

    protected void update() {

        //inputManager.update();

        if(isPaused()) {
            // Fade the current level and show the MainMenu
            return;
        }

        if(currentView == currentView.Title) {
            title.update();
            if(menu != null) {
                menu.update();
            }
            return;
        }

        if(inputManager.isDown(PlayerInputDefinitions.START)) {
            setPaused(true);
        }

        //GameMenu<PauseButton> menu = new GameMenuFX<>(PauseButton.class, null);

        if(currentLevel.getState() == LevelState.GameOver) {
            currentLevel.reset();
            gameOver.reset();

            currentLevel = gameOver;
        } else if (currentLevel.getState() == LevelState.Completed) {
            // Change to the next level
            currentLevelIndex++;

            // Just wrap around for now
            if(currentLevelIndex > allLevels.size() - 1) {
                // We beat all the levels, just go to credits
                currentLevelIndex = 0;
            }
            // cleanup remove the old level here
            currentLevel = allLevels.get(currentLevelIndex);

            currentLevel.reset();
        }

        currentLevel.update();
    }

    protected void draw() {

        if(currentView == currentView.Title) {
            title.draw();
            return;
        }

        if(isPaused()) {
            // Just return
            return;
        }

        // Otherwise we're playing
        currentLevel.draw();
    }
}
*/