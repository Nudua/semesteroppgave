package com.groupname.game.core;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.background.SpaceEffect;
import com.groupname.framework.graphics.background.WeatherEffect;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.math.Size;
import com.groupname.game.other.CreditsScroll;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreditsScreen extends GameEngine {

    private final SpriteBatch spritebatch;
    private final InputManager inputManager;

    private SpaceEffect spaceEffect;

    private CreditsScroll creditsScroll;

    public CreditsScreen(Pane parent, Scene scene, int width, int height) {
        super(parent, width, height);

        inputManager = new InputManager(scene);
        spritebatch = new SpriteBatch(graphicsContext);
        background = Color.BLACK;

        loadResources();
    }

    private void loadResources() {
        createSpaceEffect();
    }

    private void createSpaceEffect() {
        String spriteSheetFolder = "../resources/graphics/spritesheets/";
        Image star = new Image(getClass().getResourceAsStream(spriteSheetFolder + "star.png"));
        spritebatch.addSpritesheet("star", star);

        Sprite bigStar = new Sprite.Builder("smallStar", "star", new Size(32,32)).scale(0.7).build();
        Sprite mediumStar = new Sprite.Builder("smallStar", "star", new Size(16,16)).sourceOffset(0,40).scale(0.7).build();
        Sprite smallStar = new Sprite.Builder("smallStar", "star", new Size(8,8)).sourceOffset(0,64).scale(0.5).build();

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
