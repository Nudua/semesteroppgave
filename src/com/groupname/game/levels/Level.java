package com.groupname.game.levels;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.game.other.PlayerComparator;
import com.groupname.game.core.Game;
import com.groupname.game.editor.metadata.LevelMetaData;
import com.groupname.game.data.AppSettings;
import com.groupname.game.editor.metadata.LevelFactory;
import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.levels.core.LevelState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This class generates gameObjects based on the LevelMetaData that is given.
 */
public class Level extends LevelBase {

    private final LevelMetaData levelMetaData;
    private Image backgroundImage;

    private int screenWidth = AppSettings.SCREEN_BOUNDS.getWidth();
    private int screenHeight = AppSettings.SCREEN_BOUNDS.getHeight();

    private Runnable onPlayerDead = null;

    /**
     * Creates a new instance of this level with the specified LevelMetaData to generate the contents of this level.
     * @param parent the owner of this game.
     * @param graphicsContext the context used to draw this level.
     * @param levelMetaData the metaData used to create the content for this level.
     */
    public Level(Game parent, GraphicsContext graphicsContext, LevelMetaData levelMetaData) {
        super(parent, graphicsContext);
        this.levelMetaData = Objects.requireNonNull(levelMetaData);
    }

    /**
     * Sets a runnable that gets executed when the player dies.
     *
     * @param onPlayerDead the runnable to set.
     */
    public void setOnPlayerDead(Runnable onPlayerDead) {
        this.onPlayerDead = onPlayerDead;
    }

    /**
     * Returns the ID of this "level".
     *
     * @return the ID of this "level".
     */
    @Override
    public String getId() {
        return levelMetaData.getId();
    }

    /**
     * Loads and initializes all the gameObjects used by this level
     * from the LevelMetaData specified by the constructor.
     */
    @Override
    public void initialize() {
        gameObjects.clear();

        backgroundImage = Content.loadImage(levelMetaData.getBackgroundImagePath(), ResourceType.BACKGROUND);

        List<ObjectMetaData> objectsMetaData = levelMetaData.getObjectMetaDataList();

        LevelFactory factory = new LevelFactory(inputManager);

        objectsMetaData.sort(new PlayerComparator());

        for(ObjectMetaData objectMetaData : objectsMetaData) {
            GameObject gameObject = factory.create(objectMetaData);

            if(gameObject instanceof Player) {
                factory.setPlayer((Player)gameObject);
            }

            gameObjects.add(gameObject);
        }

        System.out.println("Loaded level successfully: " + levelMetaData.getId());
        initialized = true;
    }

    /**
     * Updates the game logic for this level.
     */
    @Override
    public void update() {

        gameObjects.forEach(GameObject::update);

        Optional<GameObject> player = gameObjects.stream().filter(n -> n instanceof Player).findFirst();

        if(player.isPresent()) {
            Player player1 = (Player)player.get();
            player1.checkCollision(gameObjects);
        }

        if(allEnemiesDead()) {
            state = LevelState.COMPLETED;
        }

        if(playerDead()) {
            state = LevelState.GAME_OVER;
            if(onPlayerDead != null) {
                onPlayerDead.run();
            }
        }
    }

    private boolean playerDead() {
        return gameObjects.parallelStream()
                .filter(n -> n instanceof Player)
                .noneMatch(n -> ((Player) n).isAlive());
    }

    private boolean allEnemiesDead() {
        if(gameObjects.size() == 0) {
            return true;
        }

        return gameObjects.parallelStream()
                .filter(n -> n instanceof Enemy)
                .noneMatch(n -> ((Enemy) n).isAlive());
    }

    /**
     * Draws the level.
     */
    @Override
    public void draw() {
        // Draw background
        graphicsContext.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight);

        // Can't do this!
        //gameObjects.parallelStream().forEach(gameObject -> { gameObject.draw(spriteBatch); });

        for(GameObject gameObject : gameObjects) {
            gameObject.draw(spriteBatch);
        }

    }

    /**
     * Resets this level to it's original state.
     */
    @Override
    public void reset() {
        initialize();
        super.reset();
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return super.toString() + "Level{" +
                "levelMetaData=" + levelMetaData +
                ", backgroundImage=" + backgroundImage +
                ", screenWidth=" + screenWidth +
                ", screenHeight=" + screenHeight +
                ", onPlayerDead=" + onPlayerDead +
                '}';
    }
}
