package com.groupname.game.other;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.background.WeatherEffect;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteBatchFX;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.framework.math.Size;
import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.input.PlayerInputDefinitions;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameOverScreen extends GameEngine {

    private final SpriteBatch spritebatch;
    private final InputManager inputManager;

    private WeatherEffect weatherEffect;

    public GameOverScreen(Pane parent, int width, int height) {
        super(parent, width, height);

        inputManager = new InputManager(scene);
        spritebatch = new SpriteBatchFX(graphicsContext);
        background = Color.BLACK;

        loadResources();
    }

    private void loadResources() {
        createWeatherEffect();
    }

    private void createWeatherEffect() {
        String spriteSheetFolder = "../resources/graphics/spritesheets/";
        Image snow = new Image(getClass().getResourceAsStream(spriteSheetFolder + "snow.png"));
        SpriteSheet snowSheet = new SpriteSheet("snow", snow);


        /*
        SpriteOld snowSprite1 = new SpriteOld.Builder("snowflake", "snow", new Size(30,20)).build();
        SpriteOld snowSprite2 = new SpriteOld.Builder("snowflake", "snow", new Size(30,20)).sourceVector(1,0).build();
        SpriteOld snowSprite3 = new SpriteOld.Builder("snowflake", "snow", new Size(30,20)).sourceVector(2,0).scale(0.9).build();
        SpriteOld snowSprite4 = new SpriteOld.Builder("snowflake", "snow", new Size(30,20)).sourceVector(3,0).build();
        */

        Sprite snowSprite3 = new Sprite(snowSheet, Sprite.createSpriteRegion(2,0,30,20));
        snowSprite3.setScale(0.9d);

        List<Sprite> sprites = new ArrayList<>(Arrays.asList(snowSprite3));

        weatherEffect = new WeatherEffect(sprites, new Size(1280, 720));
    }

    protected void update() {

        // Update input to the most recent state
        if(inputManager.isEnabled()) {
            inputManager.update();
        }

        weatherEffect.update();

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

        drawWeather();
    }

    private void drawWeather() {
        weatherEffect.draw(spritebatch);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(Font.font(80));
        graphicsContext.fillText("Game Over ...", 1280 / 2 - 250, 720 / 2);
    }
}
