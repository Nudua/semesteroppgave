package com.groupname.framework.graphics.background.effects.space;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.core.UpdateDrawAble;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import static com.groupname.framework.graphics.background.effects.space.Star.Type;

/**
 * This class will create 100 small, 30 medium and 10 big stars with each group moving at different speed,
 * giving the illusion of a space effect.
 */
public class SpaceEffect implements UpdateDrawAble {

    private final List<Sprite> spriteList;
    private final List<GameObject> stars;
    private final Size screenBounds;

    /**
     * Creates a new instance of this class with the specified sprites.
     *
     * Note: the spriteList must contain 3 sprites.
     *
     * @param spriteList a list containing 3 sprites that will be used for generating the stars for this effect.
     * @param screenBounds the bounds of the screen.
     */
    public SpaceEffect(List<Sprite> spriteList, Size screenBounds) {
        this.spriteList = Objects.requireNonNull(spriteList);

        // SpriteList must have 3 sprites for the different sizes of the stars
        if(spriteList.size() != 3) {
            throw new InvalidParameterException();
        }

        this.screenBounds = Objects.requireNonNull(screenBounds);

        stars = new ArrayList<>();

        createStars();
    }

    private void createStars() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        Type[] starTypes = Star.Type.values();
        int[] starCount = {100, 30, 10};
        //int[] starCount = {1000, 500, 250};

        // Create 20 stars for each type and place them at a random position
        for(int z = 0; z < starTypes.length; z++) {

            Type starType = starTypes[z];
            int count = starCount[z];

            for(int i = 0; i < count; i++) {
                double x = random.nextDouble(0, screenBounds.getWidth());
                double y = random.nextDouble(0, screenBounds.getHeight());

                Vector2D position = new Vector2D(x,y);

                Sprite starSprite = Star.getSpriteFromType(starType, spriteList);

                Star star = new Star(starSprite, position, starType);
                stars.add(star);
            }
        }

    }

    /**
     * Updates the logic required to move all the stars drawn by this effect.
     */
    @Override
    public void update() {
        for(GameObject star : stars) {
            star.update();
        }
    }

    /**
     * Draws all the stars used by this instance.
     *
     * @param spriteBatch the spriteBatch used for drawing any sprites.
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        for(GameObject star : stars) {
            star.draw(spriteBatch);
        }
    }

    @Override
    public String toString() {
        return "SpaceEffect{" +
                "spriteList=" + spriteList +
                ", stars=" + stars +
                ", screenBounds=" + screenBounds +
                '}';
    }
}
