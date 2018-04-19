package com.groupname.game.levels;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.game.core.Game;
import com.groupname.game.core.LevelMetaData;
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

public class Level extends LevelBase {

    private final LevelMetaData levelMetaData;
    private Image backgroundImage;

    private int screenWidth = AppSettings.SCREEN_BOUNDS.getWidth();
    private int screenHeight = AppSettings.SCREEN_BOUNDS.getHeight();

    private Runnable onPlayerDead = null;

    public Level(Game parent, GraphicsContext graphicsContext, LevelMetaData levelMetaData) {
        super(parent, graphicsContext);
        this.levelMetaData = Objects.requireNonNull(levelMetaData);
    }

    public void setOnPlayerDead(Runnable onPlayerDead) {
        this.onPlayerDead = onPlayerDead;
    }

    @Override
    public String getId() {
        return levelMetaData.getId();
    }

    @Override
    public void initialize() {
        gameObjects.clear();

        backgroundImage = Content.loadImage(levelMetaData.getBackgroundImagePath(), ResourceType.Background);

        List<ObjectMetaData> objectsMetaData = levelMetaData.getObjectMetaDataList();

        LevelFactory factory = new LevelFactory(inputManager);

        for(ObjectMetaData objectMetaData : objectsMetaData) {
            GameObject gameObject = factory.create(objectMetaData);
            gameObjects.add(gameObject);
        }

        System.out.println("Loaded level successfully: " + levelMetaData.getId());
    }

    @Override
    public void update() {

        gameObjects.stream().forEach(GameObject::update);

        Optional<GameObject> player = gameObjects.stream().filter(n -> n instanceof Player).findFirst();

        if(player.isPresent()) {
            Player player1 = (Player)player.get();
            player1.checkCollision(gameObjects);
        }


        /*
        for(GameObject gameObject : gameObjects) {
            gameObject.update();

            if(gameObject instanceof Player) {
                ((Player) gameObject).checkCollision(gameObjects);
            }
        }
        */

        if(allEnemiesDead()) {
            state = LevelState.Completed;
        }

        if(playerDead()) {
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

    @Override
    public void reset() {
        initialize();
        super.reset();
    }
}
