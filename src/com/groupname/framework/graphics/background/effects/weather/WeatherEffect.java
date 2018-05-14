package com.groupname.framework.graphics.background.effects.weather;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.core.UpdateDrawAble;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Represents a weather effect that blows 10,000 WindFlakes across the screen.
 */
public class WeatherEffect implements UpdateDrawAble {
    private final Size screenBounds;

    private final Queue<GameObject> flakes;
    private final List<Sprite> sprites;

    private static final int FLAKE_COUNT = 10000;

    /**
     * Creates a new instance of this effect with the specified list of sprites used for the WindFlakes generated.
     * the sprites list must not be null and must have at least one sprite.
     *
     * @param sprites the list of sprites to use for the WindFlakes.
     * @param screenBounds the bounds of the screen.
     */
    public WeatherEffect(List<Sprite> sprites, Size screenBounds) {
        if(sprites == null) {
            throw new NullPointerException();
        }

        if(sprites.size() == 0) {
            throw new InvalidParameterException("This list must contain at least one sprite");
        }

        this.sprites = sprites;
        this.screenBounds = Objects.requireNonNull(screenBounds, "screenBounds cannot be null");

        flakes = new ConcurrentLinkedQueue<>();

        createWindFlakes();
    }

    private void createWindFlakes() {
        ThreadLocalRandom rng = ThreadLocalRandom.current();

        // Create 10k snow flakes in parallel
        IntStream.range(0, FLAKE_COUNT).parallel().forEach(n -> createAndAddNewFlake(rng));
    }

    // Generate a new randomized wind flake using the given rng provider.
    private void createAndAddNewFlake(ThreadLocalRandom rng) {
        WindFlake flake = new WindFlake(sprites.get(rng.nextInt(0, sprites.size())), new Vector2D(rng.nextInt(0, screenBounds.getWidth()), rng.nextInt(0, screenBounds.getHeight())), screenBounds, rng.nextInt(0, 60), rng.nextDouble(WindFlake.MIN_WIND_SPEED, WindFlake.MAX_WIND_SPEED));
        flakes.add(flake);
    }

    /**
     * Updates the logic for all the windFlakes used by this instance in parallel.
     */
    @Override
    public void update() {
        flakes.parallelStream().forEach(GameObject::update);
    }

    /**
     * Draws all the wind flakes used by this object.
     *
     * @param spriteBatch the spriteBatch used for drawing any sprites.
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        for(GameObject flake : flakes) {
            flake.draw(spriteBatch);
        }
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "WeatherEffect{" +
                "screenBounds=" + screenBounds +
                ", flakes=" + flakes +
                ", sprites=" + sprites +
                '}';
    }
}
