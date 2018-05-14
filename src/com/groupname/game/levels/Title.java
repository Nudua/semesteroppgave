package com.groupname.game.levels;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.core.Game;
import com.groupname.game.editor.metadata.EnemyMetaData;
import com.groupname.game.editor.metadata.LevelObjectFactory;
import com.groupname.game.entities.EnemySpriteType;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.levels.core.LevelBase;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * The title screen.
 * The first thing you see when opening the game.
 */
public class Title extends LevelBase {

    private static final String LEVEL_ID = "0045c879-f50f-4918-b0f6-b213f7e2b522";
    private Image backgroundImage;

    /**
     * Creating a new instance of this class.
     *
     * @param parent the game used to update this "level".
     * @param graphicsContext the graphics context used to draw this "level".
     */
    public Title(Game parent, GraphicsContext graphicsContext) {
        super(parent, graphicsContext);

        backgroundColor = Color.rgb(53, 188, 248);
    }

    /**
     * Returns the ID of this "level".
     *
     * @return the ID of this "level".
     */
    @Override
    public String getId() {
        return LEVEL_ID;
    }


    /**
     * Initialize the "level".
     */
    @Override
    public void initialize() {
        backgroundImage = Content.loadImage("hill.png", ResourceType.BACKGROUND);
        createEnemies();
    }

    private void createEnemies() {
        LevelObjectFactory levelObjectFactory = new LevelObjectFactory(inputManager);

        EnemyMetaData worm = new EnemyMetaData("Worm", GuardEnemy.class);
        worm.setSpriteType(EnemySpriteType.GREEN_WORM);
        worm.setPosition(new Vector2D(400,50));

        EnemyMetaData blueBlob = new EnemyMetaData("Blob", GuardEnemy.class);
        blueBlob.setSpriteType(EnemySpriteType.BLUE_BLOB);
        blueBlob.setPosition(new Vector2D(700,600));

        gameObjects.add(levelObjectFactory.create(worm));
        gameObjects.add(levelObjectFactory.create(blueBlob));
    }

    /**
     * Method that update the logic at this "level".
     */
    @Override
    public void update() {
        for(GameObject gameObject : gameObjects) {
            gameObject.update();
        }
    }

    /**
     * Draws the level.
     */
    @Override
    public void draw() {
        clearScreen();
        graphicsContext.drawImage(backgroundImage, 100,420);

        for(GameObject gameObject : gameObjects) {
            gameObject.draw(spriteBatch);
        }
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return super.toString() +
                "Title{" +
                "backgroundImage=" + backgroundImage +
                '}';
    }
}
