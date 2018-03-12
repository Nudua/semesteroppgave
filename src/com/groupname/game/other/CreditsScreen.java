package com.groupname.game.other;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.background.SpaceEffect;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteBatchFX;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.framework.math.IntVector2D;
import com.groupname.framework.math.Size;
import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.other.CreditsScroll;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

// Should probably be converted into a Level
public class CreditsScreen extends GameEngine {

    private final SpriteBatch spritebatch;
    private final InputManager inputManager;

    private SpaceEffect spaceEffect;

    private CreditsScroll creditsScroll;

    public CreditsScreen(Pane parent, int width, int height) {
        super(parent, width, height);

        inputManager = new InputManager(scene);
        spritebatch = new SpriteBatchFX(graphicsContext);
        background = Color.BLACK;

        loadResources();
    }

    private void loadResources() {
        createSpaceEffect();
    }

    private void createSpaceEffect() {
        String spriteSheetFolder = "../resources/graphics/spritesheets/";
        Image star = new Image(getClass().getResourceAsStream(spriteSheetFolder + "star.png"));
        SpriteSheet starSpriteSheet = new SpriteSheet("star", star);

        Sprite bigStar = new Sprite("smallStar", starSpriteSheet, Sprite.createSpriteRegion(32,32));
        bigStar.setScale(0.7d);

        Sprite mediumStar = new Sprite("mediumStar", starSpriteSheet, Sprite.createSpriteRegion(0,0,16,16, new IntVector2D(0,40)));
        mediumStar.setScale(0.7d);

        Sprite smallStar = new Sprite("smallStar", starSpriteSheet, Sprite.createSpriteRegion(0,0,8,8, new IntVector2D(0,64)));
        smallStar.setScale(0.5d);

        List<Sprite> sprites = Arrays.asList(smallStar, mediumStar, bigStar);

        spaceEffect = new SpaceEffect(sprites, new Size(1280, 720));

        creditsScroll = new CreditsScroll();
    }

    protected void update() {

        // Update input to the most recent state
        if(inputManager.isEnabled()) {
            inputManager.update();
        }

        spaceEffect.update();
        //weatherEffect.update();
        creditsScroll.update();

        if(inputManager.wasPressed(PlayerInputDefinitions.SELECT)) {
            SceneManager sceneManager = SceneManager.INSTANCE;
            sceneManager.changeToScene(SceneName.Title);
            stop();
        }

    }

    protected void draw() {
        // Clear the entire screen with the default background color
        graphicsContext.setFill(background);
        graphicsContext.fillRect(0, 0, width, height);

        //drawWeather();
        drawSpace();
    }

    private void drawSpace() {
        spaceEffect.draw(spritebatch);

        creditsScroll.draw(graphicsContext);
    }
}
