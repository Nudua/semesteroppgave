package com.groupname.game.levels;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.background.space.SpaceEffect;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.IntVector2D;
import com.groupname.game.core.Game;
import com.groupname.game.data.AppSettings;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.framework.graphics.background.ScrollingText;
import com.groupname.game.levels.core.LevelState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

// Should probably be converted into a Level
public class Credits extends LevelBase {

    private SpaceEffect spaceEffect;
    private ScrollingText scrollingText;

    public Credits(Game game, GraphicsContext graphicsContext) {
        super(game, graphicsContext);

        backgroundColor = Color.BLACK;
    }

    @Override
    public void initialize() {
        createSpaceEffect();
        initialized = true;
    }

    private void createSpaceEffect() {
        SpriteSheet starSpriteSheet = new SpriteSheet("star", Content.loadImage("star.png", ResourceType.SpriteSheet));

        Sprite bigStar = new Sprite(starSpriteSheet, Sprite.createSpriteRegion(32,32));
        bigStar.setScale(0.7d);

        Sprite mediumStar = new Sprite( starSpriteSheet, Sprite.createSpriteRegion(0,0,16,16, new IntVector2D(0,40)));
        mediumStar.setScale(0.7d);

        Sprite smallStar = new Sprite(starSpriteSheet, Sprite.createSpriteRegion(0,0,8,8, new IntVector2D(0,64)));
        smallStar.setScale(0.5d);

        List<Sprite> sprites = Arrays.asList(smallStar, mediumStar, bigStar);

        spaceEffect = new SpaceEffect(sprites, AppSettings.SCREEN_BOUNDS);
        scrollingText = new ScrollingText();
    }

    @Override
    public void update() {
        // Update input to the most recent state
        if(inputManager.isEnabled()) {
            inputManager.update();
        }

        if(inputManager.wasPressed(PlayerInputDefinitions.SELECT)) {
            state = LevelState.Completed;
        }

        spaceEffect.update();
        scrollingText.update();
    }

    @Override
    public void draw() {
        clearScreen();
        drawSpace();
    }

    private void drawSpace() {
        spaceEffect.draw(spriteBatch);
        scrollingText.draw(graphicsContext);
    }
}
