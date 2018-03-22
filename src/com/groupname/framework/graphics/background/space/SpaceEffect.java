package com.groupname.framework.graphics.background.space;

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
import static com.groupname.framework.graphics.background.space.Star.StarType;

public class SpaceEffect implements UpdateDrawAble {

    private final List<Sprite> spriteList;
    private final List<GameObject> stars;
    private final Size screenBounds;

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

        StarType[] starTypes = StarType.values();
        int[] starCount = {100, 30, 10};
        //int[] starCount = {1000, 500, 250};

        // Create 20 stars for each type and place them at a random position
        for(int z = 0; z < starTypes.length; z++) {

            StarType starType = starTypes[z];
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

    @Override
    public void update() {
        for(GameObject star : stars) {
            star.update();
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        for(GameObject star : stars) {
            star.draw(spriteBatch);
        }
    }
}
