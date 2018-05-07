package com.groupname.game.levels;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.background.effects.weather.WeatherEffect;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.game.core.Game;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.levels.core.LevelState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This level represents the Game Over level that is shown when the player dies.
 */
public class GameOver extends LevelBase {

    public static final String LEVEL_ID = "493b0589-2308-4415-aeef-071856b11da4";

    private WeatherEffect weatherEffect;

    /**
     * Creates a new instance of this class with the specified game and graphicscontext.
     *
     * @param game the game used to run this level.
     * @param graphicsContext the graphics context used to draw this level.
     */
    public GameOver(Game game, GraphicsContext graphicsContext) {
        super(game, graphicsContext);

        backgroundColor = Color.BLACK;
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
     * Initialize the level.
     */
    @Override
    public void initialize() {
        createWeatherEffect();
        initialized = true;
    }

    private void createWeatherEffect() {
        SpriteSheet snowSheet = new SpriteSheet("snow", Content.loadImage("snow.png", ResourceType.SPRITE_SHEET));

        Sprite snowSprite3 = new Sprite(snowSheet, Sprite.createSpriteRegion(2,0,30,20));
        snowSprite3.setScale(1.0d);

        List<Sprite> sprites = new ArrayList<>(Arrays.asList(snowSprite3));

        weatherEffect = new WeatherEffect(sprites, screenBounds);
    }

    /**
     * Method that update the logic of this "level".
     */
    @Override
    public void update() {
        weatherEffect.update();

        if(inputManager.wasPressed(PlayerInputDefinitions.SELECT)) {
            state = LevelState.COMPLETED;
        }
    }

    /**
     * Draws the level.
     */
    @Override
    public void draw() {
        clearScreen();
        drawWeather();
    }

    private void drawWeather() {
        weatherEffect.draw(spriteBatch);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(Font.font(80));
        graphicsContext.fillText("GAME Over ...", 1280 / 2 - 250, 720 / 2);
    }
}
