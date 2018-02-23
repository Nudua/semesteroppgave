package com.groupname.framework.graphics.background;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class WeatherEffect {
    private boolean enabled = true;

    //private final SpriteOld sprite;
    private final Size screenBounds;

    private final List<GameObject> flakes;
    private final List<Sprite> sprites;

    public WeatherEffect(List<Sprite> sprites, Size screenBounds) {
        this.sprites = Objects.requireNonNull(sprites, "sprite cannot be null");
        this.screenBounds = Objects.requireNonNull(screenBounds, "screenBounds cannot be null");

        flakes = new ArrayList<>();

        //initializeFlakes();
        createWindFlakes();
    }

    private void createWindFlakes() {
        ThreadLocalRandom rng = ThreadLocalRandom.current();

        // 10000
        for(int i = 0; i < 10000; i++) {
            WindFlake flake = new WindFlake(sprites.get(rng.nextInt(0, sprites.size())), new Vector2D(rng.nextInt(0, screenBounds.getWidth()), rng.nextInt(0, screenBounds.getHeight())), screenBounds, rng.nextInt(0, 60), rng.nextDouble(WindFlake.MIN_WIND_SPEED, WindFlake.MAX_WIND_SPEED));
            flakes.add(flake);
        }
    }

    private void initializeFlakes() {
        int width = screenBounds.getWidth();

        ThreadLocalRandom rng = ThreadLocalRandom.current();


        for(int x = 0; x < width; x++) {

            // Create a new snowflake every 20-ish pixels
            if(x % 10 == 0) {
                WeatherFlake flake = new WeatherFlake(sprites.get(0), new Vector2D(rng.nextInt(x - 2, x + 2), rng.nextInt(0, screenBounds.getHeight())), screenBounds, rng.nextBoolean(), rng.nextInt(0,60));
                flakes.add(flake);
            }

        }

        //WeatherFlake flake = new WeatherFlake(sprite, new Vector2D(200,100), screenBounds, rng.nextBoolean());
        //flakes.add(flake);

    }

    public void update() {
        for(GameObject flake : flakes) {
            flake.update();
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        for(GameObject flake : flakes) {
            flake.draw(spriteBatch);
        }
    }
}
