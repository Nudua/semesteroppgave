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

public class SpriteFactory {
    public final Map<String, SpriteSheet> spriteSheets;
    private final static String ENEMY_SPRITESHEET_FILENAME = "enemies.png";
    private final static String PLAYER_SPRITESHEET_FILENAME = "alien-works.png";
    private final static String ENEMY_SPRITESHEET = "enemies";
    private final static String PLAYER_SPRITESHEET = "player1";
    private final static String PROJECTILE_SPRITESHEET_FILENAME = "prosjectile.png";
    private final static String PROJECTILE_SPRITESHEET = "projectile";


    public SpriteFactory() {
        spriteSheets = new HashMap<>();
        createSpriteSheets();
    }

    private void createSpriteSheets() {
        Image playerSheet = Content.loadImage(PLAYER_SPRITESHEET_FILENAME, ResourceType.SpriteSheet);
        Image enemySheet = Content.loadImage(ENEMY_SPRITESHEET_FILENAME, ResourceType.SpriteSheet);

        spriteSheets.put(PLAYER_SPRITESHEET, new SpriteSheet(PLAYER_SPRITESHEET, playerSheet));
        spriteSheets.put(ENEMY_SPRITESHEET, new SpriteSheet(ENEMY_SPRITESHEET, enemySheet));
    }

    public Sprite createEnemy(EnemySpriteType spritetype) {
        Objects.requireNonNull(spritetype);

        int y = spritetype.getIndex();

        AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(0, y, 80, 80), 20);
        AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(1, y, 80, 80), 20);
        AnimatedSprite sprite = new AnimatedSprite(spriteSheets.get(ENEMY_SPRITESHEET), frame1.getSpriteRegion(), Arrays.asList(frame1, frame2));


        return sprite;
    }

    public Sprite createPlayer() {
        // Idle animation, player class need a lot of work for animations!
        AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(0,0, 124, 124), 60 * 2);
        AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(1,0, 124, 124), 60);
        AnimationFrame frame3 = new AnimationFrame(Sprite.createSpriteRegion(2,0, 124, 124), 60);
        AnimationFrame frame4 = new AnimationFrame(Sprite.createSpriteRegion(1,0, 124, 124), 60);

        AnimatedSprite animatedSprite = new AnimatedSprite(spriteSheets.get(PLAYER_SPRITESHEET), frame1.getSpriteRegion(), Arrays.asList(frame1, frame2, frame3, frame4));

        //Sprite p1Sprite = new Sprite(spriteSheets.get("player1"), Sprite.createSpriteRegion(2,0, 124, 124));
        animatedSprite.setScale(0.85d);

        return animatedSprite;
    }

    public Sprite createProsjectile() {
        Image bulletSheet = Content.loadImage(PLAYER_SPRITESHEET_FILENAME, ResourceType.SpriteSheet);
        SpriteSheet bulletSpriteSheet = new SpriteSheet(PROJECTILE_SPRITESHEET, bulletSheet);

        AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(4, 0, 66, 66), 6);
        AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(3, 0, 66, 66), 6);
        AnimationFrame frame3 = new AnimationFrame(Sprite.createSpriteRegion(2, 0, 66, 66), 6);
        AnimationFrame frame4 = new AnimationFrame(Sprite.createSpriteRegion(1, 0, 66, 66), 6);

        AnimatedSprite animatedSprite = new AnimatedSprite(bulletSpriteSheet, frame1.getSpriteRegion(), Arrays.asList(frame1, frame2, frame3, frame4));

        return animatedSprite;
    }

}
