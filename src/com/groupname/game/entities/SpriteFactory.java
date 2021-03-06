package com.groupname.game.entities;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.animation.AnimationFrame;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Creates sprites for our objects based on the object type.
 */
public class SpriteFactory {
    private final Map<String, SpriteSheet> spriteSheets;
    private final static String ENEMY_SPRITESHEET_FILENAME = "enemies.png";
    private final static String PLAYER_SPRITESHEET_FILENAME = "alien-works.png";
    private final static String PROJECTILE_SPRITESHEET_FILENAME = "projectiles.png";
    private final static String HEART_SPRITESHEET_FILENAME = "heart.png";

    private final static String ENEMY_SPRITESHEET = "enemies";
    private final static String PLAYER_SPRITESHEET = "player1";
    private final static String PROJECTILE_SPRITESHEET = "projectiles";
    private final static String HEART_SPRITESHEET = "heart";


    /**
     * Creates a new instance of this object.
     */
    public SpriteFactory() {
        spriteSheets = new HashMap<>();
        createSpriteSheets();
    }

    private void createSpriteSheets() {
        Image playerSheet = Content.loadImage(PLAYER_SPRITESHEET_FILENAME, ResourceType.SPRITE_SHEET);
        Image enemySheet = Content.loadImage(ENEMY_SPRITESHEET_FILENAME, ResourceType.SPRITE_SHEET);

        spriteSheets.put(PLAYER_SPRITESHEET, new SpriteSheet(PLAYER_SPRITESHEET, playerSheet));
        spriteSheets.put(ENEMY_SPRITESHEET, new SpriteSheet(ENEMY_SPRITESHEET, enemySheet));
    }

    /**
     * Method for creating animated sprites for enemies.
     * Returns an animated sprite.
     *
     * @param spriteType the sprite type for your enemy.
     * @return an animated sprite.
     */
    public Sprite createEnemy(EnemySpriteType spriteType) {
        Objects.requireNonNull(spriteType);

        // Get the y-position within the spriteSheet
        int y = spriteType.getIndex();

        int spriteSize = 80;

        AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(0, y, spriteSize, spriteSize), 20);
        AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(1, y, spriteSize, spriteSize), 20);
        return new AnimatedSprite(spriteSheets.get(ENEMY_SPRITESHEET), frame1.getSpriteRegion(), Arrays.asList(frame1, frame2));
    }

    /**
     * Return an animated sprite of the player.
     *
     * @return an animated sprite of the player.
     */
    public Sprite createPlayer() {
        // Idle animation, player class need a lot of work for animations!
        AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(0,0, 124, 124), 60 * 2);
        AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(1,0, 124, 124), 60);
        AnimationFrame frame3 = new AnimationFrame(Sprite.createSpriteRegion(2,0, 124, 124), 60);
        AnimationFrame frame4 = new AnimationFrame(Sprite.createSpriteRegion(1,0, 124, 124), 60);

        AnimatedSprite animatedSprite = new AnimatedSprite(spriteSheets.get(PLAYER_SPRITESHEET), frame1.getSpriteRegion(), Arrays.asList(frame1, frame2, frame3, frame4));

        animatedSprite.setScale(0.85d);

        return animatedSprite;
    }

    /**
     * Returns an animated sprite of the projectile.
     *
     * @return an animated sprite of the projectile.
     */
    public Sprite createProjectile() {
        Image bulletSheet = Content.loadImage(PROJECTILE_SPRITESHEET_FILENAME, ResourceType.SPRITE_SHEET);
        SpriteSheet bulletSpriteSheet = new SpriteSheet(PROJECTILE_SPRITESHEET, bulletSheet);

        AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(4, 0, 66, 66), 6);
        AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(3, 0, 66, 66), 6);
        AnimationFrame frame3 = new AnimationFrame(Sprite.createSpriteRegion(2, 0, 66, 66), 6);
        AnimationFrame frame4 = new AnimationFrame(Sprite.createSpriteRegion(1, 0, 66, 66), 6);

        AnimatedSprite sprite = new AnimatedSprite(bulletSpriteSheet, frame1.getSpriteRegion(), Arrays.asList(frame1, frame2, frame3, frame4));
        sprite.setScale(0.5d);
        return sprite;
    }

    /**
     * Returns an array with two sprites, heart on and heart off.
     * Heart on represents a life, heart off represents a lost life.
     *
     * @return an array with two sprites, heart on and heart off.
     */
    public Sprite[] createHeart() {
        Image heart = Content.loadImage(HEART_SPRITESHEET_FILENAME, ResourceType.SPRITE_SHEET);
        SpriteSheet heartSpriteSheet = new SpriteSheet(HEART_SPRITESHEET, heart);

        Sprite on = new Sprite(heartSpriteSheet,Sprite.createSpriteRegion(300,300));
        Sprite off = new Sprite(heartSpriteSheet, Sprite.createSpriteRegion(2,0,300,300));

        on.setScale(0.2d);
        off.setScale(0.2d);

        return new Sprite[] {on, off};

    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "SpriteFactory{" +
                "spriteSheets=" + spriteSheets +
                '}';
    }
}
