package com.groupname.game.levels;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.background.effects.space.SpaceEffect;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.IntVector2D;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.core.Game;
import com.groupname.game.data.AppSettings;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.framework.graphics.background.ScrollingText;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

/**
 * This level represents the credits for this game.
 *
 * It contains a space background effect and some scrolling text with the credits.
 */
public class Credits extends LevelBase {

    public final static String LEVEL_ID = "f97998bc-7db6-43b2-ada1-1a3eecf3abf7";

    private SpaceEffect spaceEffect;
    private ScrollingText scrollingText;

    /**
     * Creates a new instance of this class with the specified game and graphicscontext.
     *
     * @param game the game used to run this level.
     * @param graphicsContext the graphics context used to draw this level.
     */
    public Credits(Game game, GraphicsContext graphicsContext) {
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
        createSpaceEffect();
        initialized = true;
    }

    private void createSpaceEffect() {
        SpriteSheet starSpriteSheet = new SpriteSheet("star", Content.loadImage("star.png", ResourceType.SPRITE_SHEET));

        Sprite bigStar = new Sprite(starSpriteSheet, Sprite.createSpriteRegion(32,32));
        bigStar.setScale(0.7d);

        Sprite mediumStar = new Sprite(starSpriteSheet, Sprite.createSpriteRegion(0,0,16,16, new IntVector2D(0,40)));
        mediumStar.setScale(0.7d);

        Sprite smallStar = new Sprite(starSpriteSheet, Sprite.createSpriteRegion(0,0,8,8, new IntVector2D(0,64)));
        smallStar.setScale(0.5d);

        List<Sprite> sprites = Arrays.asList(smallStar, mediumStar, bigStar);

        spaceEffect = new SpaceEffect(sprites, AppSettings.SCREEN_BOUNDS);

        // Slightly off-center to the left.
        double screenPosition = AppSettings.SCREEN_BOUNDS.getWidth() / 2 - 150;
        scrollingText = new ScrollingText(generateCredits(), new Vector2D(screenPosition, AppSettings.SCREEN_BOUNDS.getHeight()));
    }

    private String[] generateCredits() {
        return new String[] {"CREDITS...", "thank", "you", "for", "playing", "our", "game", "...", "now", "we", "just", "have", "to", "make", "it", "\uD83D\uDE0A"};
    }

    /**
     * Method that update the logic of this "level".
     */
    @Override
    public void update() {
        spaceEffect.update();
        scrollingText.update();
    }

    /**
     * Draws the level.
     */
    @Override
    public void draw() {
        clearScreen();
        drawSpace();
    }

    private void drawSpace() {
        spaceEffect.draw(spriteBatch);
        scrollingText.draw(graphicsContext);
    }

    /**
     * Resets the credits to the beginning.
     */
    @Override
    public void reset() {
        initialize();
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return super.toString() +
                "Credits{" +
                "spaceEffect=" + spaceEffect +
                ", scrollingText=" + scrollingText +
                '}';
    }
}
