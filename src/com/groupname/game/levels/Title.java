package com.groupname.game.levels;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.core.Game;
import com.groupname.game.entities.EnemySpriteType;
import com.groupname.game.entities.SpriteFactory;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.levels.core.LevelBase;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.nio.file.Path;
import java.util.Arrays;

public class Title extends LevelBase {

    private static final String LEVEL_ID = "0045c879-f50f-4918-b0f6-b213f7e2b522";

    public Title(Game parent, GraphicsContext graphicsContext) {
        super(parent, graphicsContext);
        createObject();

        backgroundColor = Color.rgb(53, 188, 248);
    }

    @Override
    public String getId() {
        return LEVEL_ID;
    }

    @Override
    public void initialize() {

    }

    private void createObject() {
        SpriteFactory spriteFactory = new SpriteFactory();
        Sprite sprite = spriteFactory.createEnemy(EnemySpriteType.PinkFish);
        GuardEnemy enemy1 = new GuardEnemy(sprite, new Vector2D(400,50));

        Sprite sprite2 = spriteFactory.createEnemy(EnemySpriteType.BlueBlob);
        GuardEnemy enemy2 = new GuardEnemy(sprite2, new Vector2D(700,600));

        gameObjects.addAll(Arrays.asList(enemy1, enemy2));
    }

    @Override
    public void update() {
        //inputManager.update();
        if(inputManager.isDown(PlayerInputDefinitions.DOWN)) {
            graphicsContext.setFill(Color.GREEN);
            graphicsContext.setFont(Font.font(60));
            graphicsContext.fillText("PRESSING DOWN", 10, 500);
        }

        for(GameObject gameObject : gameObjects) {
            gameObject.update();
        }

        }

    @Override
    public void draw() {
        clearScreen();
        graphicsContext.drawImage(Content.loadImage("hill.png", ResourceType.Background), 100,420);

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
