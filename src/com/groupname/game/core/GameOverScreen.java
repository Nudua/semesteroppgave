package com.groupname.game.core;

import com.groupname.framework.core.GameEngine;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.background.SpaceEffect;
import com.groupname.framework.graphics.background.WeatherEffect;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SimpleGameObject;
import com.groupname.game.other.CreditsScroll;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameOverScreen extends GameEngine {

    private final SpriteBatch spritebatch;
    private final InputManager inputManager;

    private WeatherEffect weatherEffect;

    public GameOverScreen(Pane parent, Scene scene, int width, int height) {
        super(parent, width, height);

        inputManager = new InputManager(scene);
        spritebatch = new SpriteBatch(graphicsContext);
        background = Color.BLACK;

        loadResources();
    }

    private void loadResources() {
        createWeatherEffect();
    }

    private void createWeatherEffect() {
        String spriteSheetFolder = "../resources/graphics/spritesheets/";
        Image snow = new Image(getClass().getResourceAsStream(spriteSheetFolder + "snow.png"));
        spritebatch.addSpritesheet("snow", snow);

        Sprite snowSprite1 = new Sprite.Builder("snowflake", "snow", new Size(30,20)).build();
        Sprite snowSprite2 = new Sprite.Builder("snowflake", "snow", new Size(30,20)).sourceVector(1,0).build();
        Sprite snowSprite3 = new Sprite.Builder("snowflake", "snow", new Size(30,20)).sourceVector(2,0).scale(0.9).build();
        Sprite snowSprite4 = new Sprite.Builder("snowflake", "snow", new Size(30,20)).sourceVector(3,0).build();

        List<Sprite> sprites = new ArrayList<>(Arrays.asList(snowSprite3));

        weatherEffect = new WeatherEffect(sprites, new Size(1280, 720));
    }

    protected void update() {

        // Update input to the most recent state
        if(inputManager.isEnabled()) {
            inputManager.update();
        }

        weatherEffect.update();
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
