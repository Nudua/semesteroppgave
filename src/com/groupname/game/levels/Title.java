package com.groupname.game.levels;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.core.Game;
import com.groupname.game.editor.metadata.EnemyMetaData;
import com.groupname.game.editor.metadata.LevelFactory;
import com.groupname.game.entities.EnemySpriteType;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.levels.core.LevelBase;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Title extends LevelBase {

    private static final String LEVEL_ID = "0045c879-f50f-4918-b0f6-b213f7e2b522";
    private Image backgroundImage;

    public Title(Game parent, GraphicsContext graphicsContext) {
        super(parent, graphicsContext);


        backgroundColor = Color.rgb(53, 188, 248);
    }

    @Override
    public String getId() {
        return LEVEL_ID;
    }

    @Override
    public void initialize() {
        backgroundImage = Content.loadImage("hill.png", ResourceType.BACKGROUND);
        createEnemies();
    }

    private void createEnemies() {
        /*
        SpriteFactory spriteFactory = new SpriteFactory();
        SPRITE sprite = spriteFactory.createEnemy(EnemySpriteType.PINK_FISH);
        GuardEnemy enemy1 = new GuardEnemy(sprite, new Vector2D(400,50));
        */

        /*
        SPRITE sprite2 = spriteFactory.createEnemy(EnemySpriteType.BLUE_BLOB);
        GuardEnemy enemy2 = new GuardEnemy(sprite2, new Vector2D(700,600));

        gameObjects.addAll(Arrays.asList(enemy1, enemy2));
        */

        LevelFactory levelFactory = new LevelFactory(inputManager);

        EnemyMetaData worm = new EnemyMetaData("Worm", GuardEnemy.class);
        worm.setSpriteType(EnemySpriteType.GREEN_WORM);
        worm.setPosition(new Vector2D(400,50));

        EnemyMetaData blueBlob = new EnemyMetaData("Blob", GuardEnemy.class);
        blueBlob.setSpriteType(EnemySpriteType.BLUE_BLOB);
        blueBlob.setPosition(new Vector2D(700,600));

        gameObjects.add(levelFactory.create(worm));
        gameObjects.add(levelFactory.create(blueBlob));
    }

    @Override
    public void update() {
        for(GameObject gameObject : gameObjects) {
            gameObject.update();
        }
    }

    @Override
    public void draw() {
        clearScreen();
        graphicsContext.drawImage(backgroundImage, 100,420);

        for(GameObject gameObject : gameObjects) {
            gameObject.draw(spriteBatch);
        }

       /* graphicsContext.setFill(Color.GREEN);
        graphicsContext.setFont(Font.font(60));
        graphicsContext.fillText("We're on the title screen!", 10, 50);
        graphicsContext.setFont(Font.font(30));
        graphicsContext.fillText("(add some freaken animation or something here)", 10, 85);*/

    }
}
